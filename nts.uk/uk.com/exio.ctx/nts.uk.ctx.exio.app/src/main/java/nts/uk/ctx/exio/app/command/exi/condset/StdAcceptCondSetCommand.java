package nts.uk.ctx.exio.app.command.exi.condset;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionCode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionName;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.condset.DeleteExistDataMethod;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Standard acceptance condition setting command.<br>
 * Command 受入条件設定（定型）
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StdAcceptCondSetCommand {

	/**
	 * システム種類
	 */
	private Integer systemType;

	/**
	 * 外部受入条件コード
	 */
	private String conditionSetCode;

	/**
	 * 外部受入条件名称
	 */
	private String conditionSetName;

	/**
	 * 既存データの削除
	 */
	private int deleteExistData;

	/**
	 * 受入モード
	 */
	private Integer acceptMode;

	/**
	 * 外部受入カテゴリID
	 */
	private Integer categoryId;

	/**
	 * CSVデータの項目名行
	 */
	private Integer csvDataItemLineNumber;

	/**
	 * CSVデータの取込開始行
	 */
	private Integer csvDataStartLine;

	/**
	 * 文字コード
	 */
	private Integer characterCode;

	/**
	 * 既存データの削除方法
	 */
	private Integer deleteExistDataMethod;

	/**
	 * The action
	 */
	private int action;

	private String cid;
	
	private Integer checkCompleted;
	
	public StdAcceptCondSet toDomain(String companyId) {
		return new StdAcceptCondSet(companyId,
				new AcceptanceConditionCode(this.getConditionSetCode()),
				new AcceptanceConditionName(this.getConditionSetName()),
				Optional.ofNullable(this.getSystemType() == null ? null : EnumAdaptor.valueOf(this.getSystemType(), SystemType.class)),
				Optional.ofNullable(this.getCategoryId()),
				EnumAdaptor.valueOf(this.getDeleteExistData(), NotUseAtr.class),
				Optional.ofNullable(this.getCsvDataItemLineNumber() == null ? null : new AcceptanceLineNumber(this.getCsvDataItemLineNumber())),
				Optional.ofNullable(this.getCsvDataStartLine() == null ? null : new AcceptanceLineNumber(this.getCsvDataStartLine())),
				Optional.ofNullable(this.getCharacterCode() == null ? null 
						: EnumAdaptor.valueOf(this.getCharacterCode(),ExiCharset.class)),
				NotUseAtr.NOT_USE,
				Optional.ofNullable(this.getDeleteExistDataMethod() == null ? null
						: EnumAdaptor.valueOf(this.getDeleteExistDataMethod(), DeleteExistDataMethod.class)),
				Optional.ofNullable(this.getAcceptMode() == null ? null : EnumAdaptor.valueOf(this.getAcceptMode(),AcceptMode.class)));
	}
}
