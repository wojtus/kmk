package spectrum.kmk.source;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

public class SMVParser implements Function<String, Long> {

	@Override
	public Long apply(final String t) {
		return getSMV(t);
	}

	// 617,100,000 €
	long getSMV(final String string) {
		final String withoutE = string.replace("€", "");
		final String euroString = withoutE.replace(",", "");
		final String normalized = StringUtils.normalizeSpace(euroString);
		return Long.parseLong(normalized);

	}

}
