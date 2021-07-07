interface ShiftMaster {
    shiftMasterCode: string;
    shiftMasterName: string;
    workTypeName: string;
    workTimeName: string;
    workTime1: string;
    workTime2: string;
    remark: string;
}
const TargetUnit = {
    WORKPLACE: 0,
    WORKPLACE_GROUP: 1
}
class Ksm015Data {
    mockShift: Array<any>;
    shiftGridColumns: Array<any>;
	shiftGridColumnsD: Array<any>;
	
    constructor() {
	
        this.shiftGridColumns = [
			// ver 17
            { headerText: nts.uk.resource.getText('KSM015_13'), key: 'shiftMasterCode', width: 50, formatter: _.escape  },
            { headerText: nts.uk.resource.getText('KSM015_14'), key: 'shiftMasterName', width: 50, formatter: _.escape}, 
            /*{ headerText: nts.uk.resource.getText('KSM015_15'), key: 'workTypeName', width: 100, hidden: true }, 
            { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workTimeName', width: 100, hidden: true},*/
            { headerText: nts.uk.resource.getText('KSM015_32'), key: 'workTime1', width: 200, formatter: _.escape },
            { headerText: nts.uk.resource.getText('KSM015_33'), key: 'workTime2', width: 200, formatter: _.escape },
            { headerText: nts.uk.resource.getText('KSM015_20'), key: 'remark', width: 70, formatter: _.escape }
        ];

		 this.shiftGridColumnsD = [
			// ver 17
            { headerText: nts.uk.resource.getText('KSM015_13'), key: 'shiftMasterCode', width: 50, formatter: _.escape  },
            { headerText: nts.uk.resource.getText('KSM015_14'), key: 'shiftMasterName', width: 50, formatter: _.escape}, 
/*            { headerText: nts.uk.resource.getText('KSM015_15'), key: 'workTypeName', width: 100, hidden: true }, 
            { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workTimeName', width: 100, hidden: true},*/
            { headerText: nts.uk.resource.getText('KSM015_32'), key: 'workTime1', width: 210, formatter: _.escape },
            { headerText: nts.uk.resource.getText('KSM015_33'), key: 'workTime2', width: 210, formatter: _.escape },
            { headerText: nts.uk.resource.getText('KSM015_20'), key: 'remark', width: 70, formatter: _.escape }
        ];
    
}
}