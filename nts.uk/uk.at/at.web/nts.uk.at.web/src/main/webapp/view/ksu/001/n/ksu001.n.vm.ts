module nts.uk.at.view.ksu001.n.viewmodel {

    export class ViewModel {
        listRank: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<any> = ko.observable();
        comboItems = [];
        comboColumns = [{ prop: 'name', length: 4 }];
        listEmployee: Array<any>;
        workPlaceCode:KnockoutObservable<any> = ko.observable();
        workPlaceDisplayName:KnockoutObservable<any> = ko.observable();
        constructor() {
            let self = this;
            self.listEmployee = nts.uk.ui.windows.getShared('listEmployee');
            self.comboItems.push(new ItemModel('なし', 'なし'));
            service.findAllRank().done(function(ranks) {
                _.forEach(ranks, function(rank) {
                    self.comboItems.push(new ItemModel(rank.rankCode, rank.rankCode));
                });
            });
            self.currentCodeList = ko.observableArray([]);
            if(self.listEmployee.length>0){
            let data = {
                workplaceId:self.listEmployee[0].workplaceId,
                baseDate:moment().toISOString()    
            }
            service.getWorkPlaceById(data).done((wkp)=>{
                self.workPlaceCode(wkp.workplaceCode);
                self.workPlaceDisplayName(wkp.wkpDisplayName);
            });
            }
        }

        initData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            let listEmployeeId: any = _.map(self.listEmployee, 'empId');
            service.findAllRankSetting(listEmployeeId).done(function(ranksets) {
                let rankSetarray = [];
                _.forEach(self.listEmployee, function(employee) {
                    _.forEach(ranksets, function(rankset) {
                        if (employee.empId == rankset.sid) {
                            return employee.rankCode = rankset.rankCode;
                        }
                    });

                    rankSetarray.push(new RankItems((employee.rankCode ? employee.rankCode : 'なし'), employee.empName, employee.empId, employee.empCd));
                });
                self.listRank(rankSetarray);
                dfd.resolve();
            });
            return dfd.promise();
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.initData().done(function() {
                self.initGrid();
            });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        addRankSetting(): void {
            let self = this;
            nts.uk.ui.block.invisible();
            let data: any = {};
            data.rankSetCommands = [];
            let flag = false;
            _.forEach(self.listRank(), function(value, index) {
                if (value.rankCode != value.rankCodeOld) {
                    let rankSet: any = {};
                    rankSet.sId = value.employeeId;
                    if (value.rankCode != 'なし') {
                        rankSet.rankCode = value.rankCode;
                    } else {
                        delete self.listEmployee[index].rankCode;
                    }
                    data.rankSetCommands.push(rankSet);
                    if (value.rankCodeOld != 'なし') {
                        flag = true;
                    }
                }
            });
            service.addRankSetting(data).done(function() {
                self.initData().done(function() {
                    self.initGrid();
                });
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.messageId);
            }).then(function() {
                nts.uk.ui.block.clear();
            });
        }

        /**
         * init grid 
         */
        initGrid(): void {
            let self = this;
            $("#grid2").ntsGrid({
                width: '400px',
                height: '315px',
                dataSource: self.listRank(),
                primaryKey: 'employeeCode',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: nts.uk.resource.getText("KSU001_1322"), key: 'employeeCode', dataType: 'string', width: '120px' },
                    { headerText: nts.uk.resource.getText("KSU001_1323"), key: 'rankName', dataType: 'string', width: '150px' },
                    { headerText: nts.uk.resource.getText("KSU001_1324"), key: 'rankCode', dataType: 'string', width: '100px', ntsControl: 'Combobox' },
                ],
                features: [{ name: 'Selection', mode: 'row', multipleSelection: true }],
                ntsFeatures: [],
                ntsControls: [
                    { name: 'Combobox', options: self.comboItems, optionsValue: 'rankCode', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true }
                ]
            });
        }
    }

    export class ItemModel {
        rankCode: string;
        name: string;
        constructor(rankCode: string, name: string) {
            this.rankCode = rankCode;
            this.name = name;
        }
    }
    
    export class RankItems {
        rankCode: string;
        rankName: string;
        employeeId: string;
        rankCodeOld: string;
        employeeCode: string;
        constructor(rankCode: string, rankName: string, employeeId: string, employeeCode: string) {
            this.rankCode = rankCode;
            this.rankName = rankName;
            this.employeeId = employeeId;
            this.rankCodeOld = rankCode;
            this.employeeCode = employeeCode;
        }
    }
}