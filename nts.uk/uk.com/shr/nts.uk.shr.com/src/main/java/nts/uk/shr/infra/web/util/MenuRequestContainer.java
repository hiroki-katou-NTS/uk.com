package nts.uk.shr.infra.web.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.web.util.data.MenuRequestInfo;

public class MenuRequestContainer {

	private static final Map<String, Set<MenuRequestInfo>> CONTAINER = Collections.synchronizedMap(new HashMap<>());

	private static final int DIFF_TIME_FOR_SAME_REQUEST = 5;

	private static final int TIME_FOR_CACHE = 1;

	public static void requestFromMenu(MenuRequestInfo info) {
		if (isRequestedWithIp(info)) {
			CONTAINER.get(info.getIp()).add(info);
		} else {
			CONTAINER.put(info.getIp(), Stream.of(info).collect(Collectors.toSet()));
		}
		clearStorage();
	}

	public static boolean requestedWith(MenuRequestInfo info) {
		if (isRequestedWithIp(info)) {
			boolean isRequested = CONTAINER.get(info.getIp()).stream().filter(r -> isRequested(r, info)).findFirst().isPresent();
			
//			deleteRequestSameWith(info);
			
			return isRequested;
		}
		return false;
	}

	public static void deleteRequestSameWith(MenuRequestInfo info) {
		CONTAINER.get(info.getIp()).removeIf(r -> isRequested(r, info));
	}

	private static void clearStorage() {
		GeneralDateTime now = GeneralDateTime.now();
		Map<String, Set<MenuRequestInfo>> cleared = CONTAINER.entrySet().stream().map(c -> {
			c.getValue().removeIf(r -> r.getRequestedTime().addHours(TIME_FOR_CACHE).before(now));
			if (c.getValue().isEmpty()) {
				return null;
			}
			return c;
		}).collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));

		CONTAINER.clear();
		CONTAINER.putAll(cleared);
	}

	private static boolean isRequested(MenuRequestInfo info, MenuRequestInfo target) {
		return info.getIp().equals(target.getIp()) && info.getUrl().equals(target.getUrl()) && info.getRequestedTime()
				.addMinutes(DIFF_TIME_FOR_SAME_REQUEST).afterOrEquals(target.getRequestedTime());
	}

	private static boolean isRequestedWithIp(MenuRequestInfo info) {
		return CONTAINER.containsKey(info.getIp());
	}
}
