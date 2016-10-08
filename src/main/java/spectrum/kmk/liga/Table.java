package spectrum.kmk.liga;

import java.util.Collections;
import java.util.List;

class Table {

	private final List<TableEntry> tableEntries;

	Table(final List<TableEntry> tableEntries) {
		this.tableEntries = tableEntries;
	}

	public List<TableEntry> getTableEntries() {
		return Collections.unmodifiableList(tableEntries);
	}

}
