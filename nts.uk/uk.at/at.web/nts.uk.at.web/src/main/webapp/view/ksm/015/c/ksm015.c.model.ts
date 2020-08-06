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
	let time1W = 190, remarkW = 220;
	if(window.innerWidth == 1920){
		time1W = (1920 - 792) / 3;
	}
	
	if(window.innerWidth == 1366){
		time1W = (1366 - 792) / 3;
	}
	
	$(window).resize(function () {
		let panelWidthResize = window.innerWidth - 792;
			panelWidthResize = panelWidthResize < 400 ? 400 : panelWidthResize;
			time1W = panelWidthResize/3;
			$('#shift-list').igGrid("option", "width", panelWidthResize);
			$('#form-title').css("width", panelWidthResize + "px");
			$('#shift-list_workTime2').css("width", time1W );
			$('#shift-list_workTime1').css("width", time1W);
			$('#shift-list_remark').css("width", time1W);
	});
        this.shiftGridColumns = [
			// ver 17
            { headerText: nts.uk.resource.getText('KSM015_13'), key: 'shiftMasterCode', width: 50,  },
            { headerText: nts.uk.resource.getText('KSM015_14'), key: 'shiftMasterName', width: 50,}, 
            { headerText: nts.uk.resource.getText('KSM015_15'), key: 'workTypeName', width: 100, hidden: true }, 
            { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workTimeName', width: 100, hidden: true},
            { headerText: nts.uk.resource.getText('KSM015_32'), key: 'workTime1', width: time1W },
            { headerText: nts.uk.resource.getText('KSM015_33'), key: 'workTime2', width: time1W },
            { headerText: nts.uk.resource.getText('KSM015_20'), key: 'remark', width: remarkW }
        ];

		 this.shiftGridColumnsD = [
			// ver 17
            { headerText: nts.uk.resource.getText('KSM015_13'), key: 'shiftMasterCode', width: 50,  },
            { headerText: nts.uk.resource.getText('KSM015_14'), key: 'shiftMasterName', width: 50,}, 
            { headerText: nts.uk.resource.getText('KSM015_15'), key: 'workTypeName', width: 100, hidden: true }, 
            { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workTimeName', width: 100, hidden: true},
            { headerText: nts.uk.resource.getText('KSM015_32'), key: 'workTime1', width: time1W },
            { headerText: nts.uk.resource.getText('KSM015_33'), key: 'workTime2', width: time1W },
            { headerText: nts.uk.resource.getText('KSM015_20'), key: 'remark', width: 208 }
        ];
    
}
}