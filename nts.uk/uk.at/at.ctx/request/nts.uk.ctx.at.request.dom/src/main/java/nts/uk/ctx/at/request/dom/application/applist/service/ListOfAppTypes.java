package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請種類リスト
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ListOfAppTypes {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 申請名称
	 */
	@Setter
	private String appName;
	
	/**
	 * 選択
	 */
	@Setter
	private boolean choice;
	
	/**
	 * プログラムID
	 */
	private Optional<String> opProgramID;
	
	/**
	 * 申請種類表示
	 */
	private Optional<ApplicationTypeDisplay> opApplicationTypeDisplay;
	
	/**
	 * 文字列
	 */
	private Optional<String> opString;
	
}
