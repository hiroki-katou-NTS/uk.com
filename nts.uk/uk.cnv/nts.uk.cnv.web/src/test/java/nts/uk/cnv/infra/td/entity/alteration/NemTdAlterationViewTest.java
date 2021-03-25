package nts.uk.cnv.infra.td.entity.alteration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public class NemTdAlterationViewTest {
	
	static class Dummy {
		private static DevelopmentStatus status = DevelopmentStatus.ORDERED;
		
		// 現状は先頭→未発注　末尾→検収済　だが、変更アレばメンテ必要
		private static DevelopmentStatus firstStatus = DevelopmentStatus.NOT_ORDER;
		private static DevelopmentStatus lastStatus = DevelopmentStatus.ACCEPTED;
		
		private static String aliasChar = "v"; 
		
	}

	@Test
	public void 開発進捗状況を指定するWhere句_未到達() {
		String where = NemTdAlterationView.jpqlWhere(new DevelopmentProgress(Dummy.status, false));
		assertThat(where).matches(".+?" + " is null");
	}

	@Test
	public void 開発進捗状況を指定するWhere句_到達済() {
		String where = NemTdAlterationView.jpqlWhere(new DevelopmentProgress(Dummy.status, true));
		assertThat(where).matches(".+?" + " is not null");
	}

	@Test
	public void 開発状況を指定するWhere句_先頭状況() {
		String where = NemTdAlterationView.jpqlWhere(Dummy.lastStatus, Dummy.aliasChar);
		assertThat(where).matches(Dummy.aliasChar + "\\." + ".+?" + " is not null");
	}

	@Test
	public void 開発状況を指定するWhere句_中間状況() {
		String where = NemTdAlterationView.jpqlWhere(Dummy.status, Dummy.aliasChar);
		assertThat(where).matches(Dummy.aliasChar + "\\." + ".+?" + " is not null"
								+ " and " 
								+ Dummy.aliasChar + "\\." + ".+?" + " is null");
	}

	@Test
	public void 開発状況を指定するWhere句_末尾状況() {
		String where = NemTdAlterationView.jpqlWhere(Dummy.firstStatus, Dummy.aliasChar);
		assertThat(where).matches(Dummy.aliasChar + "\\." + ".+?" + " is null");
	}
}
