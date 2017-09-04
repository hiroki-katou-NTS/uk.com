package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CloseAtr {
	
	//産前休業
	PRENATAL(0),
	//産後休業
	POSTPARTUM(1),
	//育児休業
	CHILD_CARE(2),
	//介護休業
	CARE(3),
	//傷病休業
	INJURY_OR_ILLNESS(4);
	
    public final int value;
}
