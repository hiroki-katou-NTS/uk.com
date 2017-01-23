package nts.uk.ctx.basic.dom.company.useset;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * @author lanlt
 *
 */
@AllArgsConstructor
public class CompanyUseSet extends DomainObject{
     /**グループ会社権限*/
	 private UseSet USE_GR_SET;
	
	 /**就業権限*/
	 private UseSet USE_KT_SET ;
	
	 /**給与権限 */
	 private UseSet USE_QY_SET;
	 /**人事権限*/
	 private UseSet USE_JJ_SET;
	 /**会計権限 */
	 private UseSet USE_AC_SET;
	 /** グループウェア権限*/
	 private UseSet USE_GW_SET;
	 /** ヘルスケア権限*/
	 private UseSet USE_HC_SET;
	 /**労務コスト権限*/
	 private UseSet USE_LC_SET;
	 /** BI権限*/
	 private UseSet USE_BI_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS01_SET;
	 /** 権限予備(Reserve） */
	 private UseSet USE_RS02_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS03_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS04_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS05_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS06_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS07_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS08_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS09_SET;
	 /** 権限予備(Reserve）*/
	 private UseSet USE_RS010_SET;

 
	

}
