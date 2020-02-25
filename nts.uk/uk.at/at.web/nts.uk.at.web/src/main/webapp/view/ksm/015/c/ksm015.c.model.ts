interface ShiftMaster {
    code: string;
    name: string;
    typeOfWork: string;
    workingHours: string;
    timeZone1: string;
    timeZone2: string;
    remarks: string;
}

class Ksm015Data {
    mockShift: Array<any>;
    shiftGridColumns: Array<any>;

    constructor() {
        this.mockShift = [
            {
                code: "1",
                name: "212",
                typeOfWork: "lao cong",
                workingHours: "19h00",
                timeZone1: "JA",
                timeZone2: "V",
                remarks: "clgi",
            },
            {
                code: "2",
                name: "212",
                typeOfWork: "lao cong",
                workingHours: "19h00",
                timeZone1: "JA",
                timeZone2: "V",
                remarks: "clgi",
            },
            {
                code: "3",
                name: "212",
                typeOfWork: "lao cong",
                workingHours: "19h00",
                timeZone1: "JA",
                timeZone2: "V",
                remarks: "clgi",
            },
            {
                code: "4",
                name: "212",
                typeOfWork: "lao cong",
                workingHours: "19h00",
                timeZone1: "JA",
                timeZone2: "V",
                remarks: "clgi",
            },
            
        ];

        this.shiftGridColumns = [
            { headerText: nts.uk.resource.getText('KSM015_13'), key: 'code', width: 100,  },
            { headerText: nts.uk.resource.getText('KSM015_14'), key: 'name', width: 150,}, 
            { headerText: nts.uk.resource.getText('KSM015_15'), key: 'typeOfWork', width: 150 }, 
            { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workingHours', width: 150},
            { headerText: nts.uk.resource.getText('KSM015_32'), key: 'timeZone1', width: 150 },
            { headerText: nts.uk.resource.getText('KSM015_33'), key: 'timeZone2', width: 150 },
            { headerText: nts.uk.resource.getText('KSM015_20'), key: 'remarks', width: 150 },
        ];
    }
}