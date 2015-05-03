package org.openmrs.module.fbregistrationapp.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests converting between json and model object
 */
public class RegistrationAppConfigTest {

    @Test
    public void testParsing() throws Exception {
        RegistrationAppConfig parsed = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("registrationAppConfig.json"), RegistrationAppConfig.class);
        assertThat(parsed.getAfterCreatedUrl(), is("fbregistrationapp/findPatient.page?appId=fbregistrationapp.registerPatient"));
        assertThat(parsed.getPatientDashboardLink(), is("fbregistrationapp/registrationSummary.page"));
        assertTrue(parsed.isAllowRetrospectiveEntry());
        assertTrue(parsed.isAllowUnknownPatients());
        assertTrue(parsed.isAllowManualIdentifier());
        assertThat(parsed.getRegistrationEncounter().getEncounterType(), is("873f968a-73a8-4f9c-ac78-9f4778b751b6"));
        assertThat(parsed.getRegistrationEncounter().getEncounterRole(), is("cbfe0b9d-9923-404c-941b-f048adc8cdc0"));
        assertThat(parsed.getSections().size(), is(1));
    }
}
