class RegisterForm {
    workplaceGroupCd: KnockoutObservable<string> = ko.observable('');
    workplaceGroupName: KnockoutObservable<string> = ko.observable('');
    workplaceGroupTypes: KnockoutObservableArray<string> = ko.observableArray([
        {value: 1, name: nts.uk.resource.getText('KSM007_9')},
        {value: 2, name: nts.uk.resource.getText('KSM007_10')}
    ]);
    selectedWkpType: KnockoutObservable<number> = ko.observable(1);
    newMode: KnockoutObservable<any> = ko.observable(true);
    workplaces: KnockoutObservableArray<any> = ko.observableArray([]);
    gridColumns: Array<any> = ko.observableArray([
        { headerText: nts.uk.resource.getText('KSM007_13'), key: 'workplaceId', width: 100, hidden: true },
        { headerText: nts.uk.resource.getText('KSM007_13'), key: 'workplaceCode', width: 100,}, 
        { headerText: nts.uk.resource.getText('KSM007_14'), key: 'workplaceName', width: 200 },
        { headerText: nts.uk.resource.getText('KSM007_15'), key: 'genericName', width: 220 }
    ]);
    selectedWorkplaces: KnockoutObservableArray<any> = ko.observableArray([]);
    constructor() {
        let self = this;
    }

    public clearData() {
        let self = this;
        nts.uk.ui.errors.clearAll();
        self.workplaceGroupCd('');
        self.workplaceGroupName('');
        self.selectedWkpType(1);
        self.workplaces([]);
        self.newMode(true);
    }

    public bindData(val: IWorkplaceGroup) {
        let self = this;
        self.workplaceGroupCd(val.code);
        self.workplaceGroupName(val.name);
        self.selectedWkpType(val.type);
        self.newMode(false);
    }

    public bindWorkplace(workplaces) {
        let sorted = _.orderBy(workplaces, (val) =>  'hierarchyCode');
        this.workplaces(sorted);
    }

    public removeWorkplace() {
        let self = this;
        let filtered = _.filter(self.workplaces(), (val) => { return self.selectedWorkplaces().indexOf(val.workplaceId) === -1; });
        self.workplaces(filtered);
    }

    public trimData() {
        let self = this;
        self.workplaceGroupCd(self.workplaceGroupCd().trim());
        self.workplaceGroupName(self.workplaceGroupName().trim());
 }
    
    public convertToCommand() {
        let self = this;
        return {
            wkpGrCD: self.workplaceGroupCd(),
            wkpGrName: self.workplaceGroupName(),
            wkpGrType: self.selectedWkpType(),
            lstWKPID: _.map(self.workplaces(), "workplaceId")
        }
    }
}


interface IWorkplaceGroup {
    id: string;
    code: string;
    name: string;
    type: number;
}

interface IWorkplaceRegisterCmd {
    wkpGrID: string;
    wkpGrCD: string;
    wkpGrName: string;
    wkpGrType: string;
    lstWKPID: Array<string>;
}