package nts.uk.shr.infra.i18n.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.i18n.Locale;

public class I18NResourceContentProcessorTest {

	@Test
	public void noNestNoParam() {
		val target = new I18NResourceContentProcessor(id -> localize(id));
		
		String result = target.process(Locale.JA, "TEXT1", Arrays.asList());
		assertThat(result, is("TEXT1"));
	}

	@Test
	public void nestedNoParam() {
		val target = new I18NResourceContentProcessor(id -> localize(id));
		
		String result = target.process(Locale.JA, "TEXT2 has {#T1}!!", Arrays.asList());
		assertThat(result, is("TEXT2 has TEXT1!!"));
	}

	@Test
	public void nestedNoParam2() {
		val target = new I18NResourceContentProcessor(id -> localize(id));
		
		String result = target.process(Locale.JA, "<{#T2}>", Arrays.asList());
		assertThat(result, is("<TEXT2 has TEXT1!!>"));
	}

	@Test
	public void param2() {
		val target = new I18NResourceContentProcessor(id -> localize(id));
		
		String result = target.process(Locale.JA, "test {0} - {1}", Arrays.asList("ABC", "hello"));
		assertThat(result, is("test ABC - hello"));
	}
	
	@Test
	public void param3_resourced_param() {
		val target = new I18NResourceContentProcessor(id -> localize(id));
		
		String result = target.process(Locale.JA, "test {2} - {0} - {1}", Arrays.asList("ABC", "<{#T1}>", "にほんご"));
		assertThat(result, is("test にほんご - ABC - <TEXT1>"));
	}
	
	private static String localize(String id) {
		switch (id) {
		case "T1": return "TEXT1";
		case "T2": return "TEXT2 has {#T1}!!";
		default: return "NO TEXT";
		}
	}
	
}
