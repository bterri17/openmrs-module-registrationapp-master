package org.openmrs.module.fbregistrationapp.page.controller;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.layout.web.address.AddressSupport;
import org.openmrs.layout.web.name.NameSupport;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.fbregistrationapp.form.RegisterPatientFormBuilder;
import org.openmrs.module.fbregistrationapp.model.NavigableFormStructure;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisterPatientPageController {

    public void get(UiSessionContext sessionContext, PageModel model,
                    @RequestParam("appId") AppDescriptor app,
                    @ModelAttribute("patient") @BindParams Patient patient,
                    @SpringBean("emrApiProperties") EmrApiProperties emrApiProperties) throws Exception {

        sessionContext.requireAuthentication();
        addModelAttributes(model, patient, app, emrApiProperties.getPrimaryIdentifierType());
    }

    public void addModelAttributes(PageModel model, Patient patient, AppDescriptor app, PatientIdentifierType primaryIdentifierType) throws Exception {
        NavigableFormStructure formStructure = RegisterPatientFormBuilder.buildFormStructure(app);

        if (patient == null) {
        	patient = new Patient();
        }

        model.addAttribute("patient", patient);
        model.addAttribute("primaryIdentifierType", primaryIdentifierType);
        model.addAttribute("appId", app.getId());
        model.addAttribute("formStructure", formStructure);
        model.addAttribute("nameTemplate", NameSupport.getInstance().getDefaultLayoutTemplate());
        model.addAttribute("addressTemplate", AddressSupport.getInstance().getAddressTemplate().get(0));
        model.addAttribute("includeRegistrationDateSection", !app.getConfig().get("registrationEncounter").isNull()
                && !app.getConfig().get("allowRetrospectiveEntry").isNull()
                && app.getConfig().get("allowRetrospectiveEntry").getBooleanValue() );
        model.addAttribute("allowUnknownPatients", app.getConfig().get("allowUnknownPatients").getBooleanValue());
        model.addAttribute("allowManualIdentifier", app.getConfig().get("allowManualIdentifier").getBooleanValue());
        model.addAttribute("patientDashboardLink", app.getConfig().get("patientDashboardLink") !=null ?
                app.getConfig().get("patientDashboardLink").getTextValue() : null);
        model.addAttribute("enableOverrideOfAddressPortlet",
                Context.getAdministrationService().getGlobalProperty("addresshierarchy.enableOverrideOfAddressPortlet", "false"));
    }

}