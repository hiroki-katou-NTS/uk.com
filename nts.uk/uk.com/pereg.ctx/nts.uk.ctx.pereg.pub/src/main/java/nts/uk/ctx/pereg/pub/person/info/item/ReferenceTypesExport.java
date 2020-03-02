package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReferenceTypesExport {
	// 1:専用マスタ(DesignatedMaster)
	DESIGNATED_MASTER(1),

	// 2:コード名称(CodeName)
	CODE_NAME(2),

	// 3:列挙型(Enum)
	ENUM(3);

	public final int value;
}
