package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.haslearnt.entry.Entry.TimeType;

import java.util.*;

import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import setup.*;

public class NewEntryTest extends IntegrationTest {

	@Autowired
	EntryRepository repository;

	@Test
	public void saveNewEntry() {
		Entry entry = new Entry().iveLearnt("something").today().andItWas("easy").itTook(10, TimeType.MINUTES).build();

		repository.save(entry);

		assertNotNull(entry.id());
		Entry fetchedEntry = repository.load(entry.id());
		assertNotNull(fetchedEntry);
		assertEquals("something", fetchedEntry.what());
		assertEquals("today", fetchedEntry.when());
		assertEquals("easy", fetchedEntry.howDifficult());
		assertEquals(10, fetchedEntry.points());
	}

	@Test
	public void fetchEntryForUser() {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("hard").build();
		Entry entry2 = new Entry().today().iveLearnt("net").andItWas("hard").build();

		repository.saveEntry(entry, "tomek");
		repository.saveEntry(entry2, "tomek");

		List<Entry> fetchedEntries = repository.fetchForUser("tomek");

		assertThat(fetchedEntries).containsOnly(entry, entry2);
	}
}
