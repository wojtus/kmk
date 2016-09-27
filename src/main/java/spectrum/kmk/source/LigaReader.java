package spectrum.kmk.source;

import java.util.List;

@FunctionalInterface
public interface LigaReader<IMPORT_DTO> {

	List<IMPORT_DTO> readObjects();

}