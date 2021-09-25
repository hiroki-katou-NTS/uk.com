package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 様式９の出力レイアウト
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の出力レイアウト
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class Form9Layout implements DomainAggregate {
	
	/** コード **/
	private final Form9Code code;
	
	/** 名称 **/
	private Form9Name name;
	
	/** システム固定か **/
	private final boolean isSystemFixed;

	/** 利用区分 */
	private boolean isUse;
	
	/** 表紙 **/
	private Form9Cover cover;
	
	/** 看護職員表 **/
	private Form9NursingTable nursingTable;
	
	/** 看護補助者表 **/
	private Form9NursingAideTable nursingAideTable;
	
	/** テンプレート **/
	private Optional<String> templateFileId;
	
	/**
	 * 作る
	 * @param code コード
	 * @param name 名称
	 * @param isSystemFixed システム固定か
	 * @param isUse 利用区分
	 * @param cover 表紙
	 * @param nursingTable 看護職員表
	 * @param nursingAideTable 看護補助者表 
	 * @param templateFileId テンプレート
	 * @return
	 */
	public static Form9Layout create(Form9Code code
			,	Form9Name name, boolean isSystemFixed, boolean isUse
			,	Form9Cover cover, Form9NursingTable nursingTable
			,	Form9NursingAideTable nursingAideTable
			,	Optional<String> templateFileId) {
		if(isSystemFixed && templateFileId.isPresent()) {
			throw new BusinessException("Msg_2279");
		}
		
		if(!isSystemFixed && !templateFileId.isPresent()) {
			throw new BusinessException("Msg_2280");
		}
		
		return new Form9Layout(code, name, isSystemFixed, isUse
				,	cover, nursingTable, nursingAideTable, templateFileId);
	}
	
	/**
	 * 複製する
	 * @param require
	 * @param destinationCode 複製先コード
	 * @param destinationName 複製先名称
	 * @return
	 */
	public Form9Layout copy(Require require, Form9Code destinationCode, Form9Name destinationName) {
		
		if(!this.isSystemFixed) {
			
			return Form9Layout.create(destinationCode, destinationName, false, true, this.cover, this.nursingTable, this.nursingAideTable, this.templateFileId);
		}
		
		val fileName = this.getFileName(require);
		val fileInfo = require.saveFile(fileName);
		
		return Form9Layout.create(destinationCode, destinationName, false, true, this.cover, this.nursingTable, this.nursingAideTable, Optional.of(fileInfo.getId()) );
	}
	
	/**
	 * ファイル名を取得する
	 * @param require
	 * @return
	 */
	public String getFileName(Require require){
		if(this.isSystemFixed) {
			return this.code.v() + "_" + this.name.v() + ".xlsx";
		}
		
		Optional<StoredFileInfo> fileInfo = require.getInfo(this.templateFileId.get());
		
		return fileInfo.get().getOriginalName();
	}
	
	public static interface Require{
		/**
		 * ファイル情報を取得する
		 * @param fileId ファイルID
		 * @return
		 */
		Optional<StoredFileInfo> getInfo(String fileId);
		
		/**
		 * ファイルを保存する
		 * @param fileName ファイル名
		 * @return
		 */
		StoredFileInfo saveFile(String fileName);
	}
}
