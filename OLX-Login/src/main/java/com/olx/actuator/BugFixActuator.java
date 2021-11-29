package com.olx.actuator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "bugfixes")
public class BugFixActuator {

	private Map<String, List<String>> bugsMapList = new HashMap<String, List<String>>();

	@PostConstruct
	public void initialize() {
		bugsMapList.put("S1", Arrays.asList("bug 1", "bug 2"));
		bugsMapList.put("S2", Arrays.asList("bug 3", "bug 4"));
	}

	@ReadOperation
	public Map<String, List<String>> getAllbugs() {
		return bugsMapList;
	}

	@WriteOperation
	public void addNewVersion(@Selector String id, String bugs) {

		bugsMapList.put(id, Arrays.asList(bugs.split(",")));
		// return null;
	}

	@DeleteOperation
	public void deleteBugFixes(@Selector String version) {
		bugsMapList.remove(version);
	}

}
