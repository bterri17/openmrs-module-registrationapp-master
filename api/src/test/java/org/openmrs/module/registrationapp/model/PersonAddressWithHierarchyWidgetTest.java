package org.openmrs.module.fbregistrationapp.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests converting between json and model object
 */
public class PersonAddressWithHierarchyWidgetTest {

    @Test
    public void testParsing() throws Exception {
        PersonAddressWithHierarchyWidget parsed = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("personAddressWithHierarchy.json"), PersonAddressWithHierarchyWidget.class);
        assertThat(parsed.getProviderName(), is("fbregistrationapp"));
        assertThat(parsed.getFragmentId(), is("field/personAddressWithHierarchy"));
        assertThat(parsed.getConfig().getShortcutFor(), is("address1"));
        assertThat(parsed.getConfig().getManualFields().size(), is(1));
        assertThat(parsed.getConfig().getManualFields().get(0), is("address2"));
    }

}
