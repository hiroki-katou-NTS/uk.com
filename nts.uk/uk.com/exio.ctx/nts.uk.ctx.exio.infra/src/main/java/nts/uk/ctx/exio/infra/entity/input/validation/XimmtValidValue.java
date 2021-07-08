package nts.uk.ctx.exio.infra.entity.input.validation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.date.DateCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.integer.IntegerCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.real.RealCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.string.StringCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.time.TimeCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.time.TimeMomentCondition;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  ユーザが設定した受入値の範囲
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_VALID_VALUE")
public class XimmtValidValue extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private XimmtValidValuePK pk;
	
	@Column(name = "ITEM_TYPE")
	private int itemType;
	
	@Column(name = "COMPARE_CONDITION")
	private int compareConditon;
	
	@Column(name = "NUMERIC1")
	private BigDecimal numeric1;
	
	@Column(name = "NUMERIC2")
	private BigDecimal numeric2;
	
	@Column(name = "DATE1")
	private GeneralDate date1;

	@Column(name = "DATE2")
	private GeneralDate date2;
	
	@Column(name = "STRING1")
	private String string1;	
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtValidValue> MAPPER = new JpaEntityMapper<>(XimmtValidValue.class);
	
	public ImportingUserCondition toDomain() {
		Validation validation = getValidation();
		return new ImportingUserCondition(this.pk.getCompanyId(), this.pk.getSettingCode(), this.pk.getItemNo(), EnumAdaptor.valueOf(itemType, ItemType.class), validation);
	}

	private Validation getValidation() {
		switch(EnumAdaptor.valueOf(itemType, ItemType.class)) {
			case STRING:
				return (Validation) StringCondition.create(string1, compareConditon);
			case DATE:
				return DateCondition.create(date1, date2, compareConditon);
			case TIME_POINT:
				return TimeMomentCondition.create(numeric1.intValue(), numeric2.intValue(), compareConditon);
			case INT:
				return IntegerCondition.create(numeric1.longValue(), numeric2.longValue(), compareConditon);
			case REAL:
				return RealCondition.create(numeric1, numeric2, compareConditon);
			case TIME_DURATION:
				return TimeCondition.create(numeric1.intValue(), numeric2.intValue(), compareConditon);
			default:
				throw new RuntimeException("項目型に対する実装が存在しません。:" + EnumAdaptor.valueOf(itemType, ItemType.class));
		}
	}
}
