module nts.uk.at.view.ksu001.s.sb {
    import setShare = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.ksu001.s.sb.service;
    // Import
    export module viewmodel {
        export class ScreenModel {
            lstEmp: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).lstEmp;
            date: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).date;
            selectedEmployeeSwap: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).selectedEmployeeSwap;
            empCodeName: KnockoutObservable<any>
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns2: KnockoutObservableArray<any>;
            
            constructor() {
                this.items = ko.observableArray([]);
                this.currentCode = ko.observable();
                this.columns2 = ko.observableArray([]);
                this.empCodeName =  ko.observable();
            }

            public startPage(): JQueryPromise<any> {
                let self = this,
                    dataShare = nts.uk.ui.windows.getShared('KSU001SB');
            
                let dfd = $.Deferred();

                let param = {
                    listEmpId: dataShare.lstEmp.listEmpId,
                    date: dataShare.date,
                    selectedEmployeeSwap: dataShare.selectedEmployeeSwap
                }

                service.getData(param).done(function(data: any) {
                    console.log(data);
                    let key1, key2, key3, key4, key5;
                    key1 = self.createKey(data.lstOrderColumn[0].sortName);
                    key2 = self.createKey(data.lstOrderColumn[1].sortName);
                    key3 = self.createKey(data.lstOrderColumn[2].sortName);
                    key4 = self.createKey(data.lstOrderColumn[3].sortName);
                    key5 = self.createKey(data.lstOrderColumn[4].sortName);
                    
                    
                        
                    self.columns2 = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 100, hidden: true },
                        { headerText: 'コード／名称', key: 'name', width: 230 },
                        { headerText: data.lstOrderColumn[0].sortName, key: key1, width: 100 },
                        { headerText: data.lstOrderColumn[1].sortName, key: key2, width: 100 },
                        { headerText: data.lstOrderColumn[2].sortName, key: key3, width: 100 },
                        { headerText: data.lstOrderColumn[3].sortName, key: key4, width: 100 },
                        { headerText: data.lstOrderColumn[4].sortName, key: key5, width: 100 }
                    ]);
                    _.forEach(data.lstEmpId, function(item) {
                        let lstEmpInforATR = _.find(data.lstEmpInforATR, function(o) { return o.empID == item; });
                        let lstEmpTeamInforDto = _.find(data.lstEmpTeamInforDto, function(o) { return o.employeeID == item; });
                        let lstEmpRankInforDto = _.find(data.lstEmpRankInforDto, function(o) { return o.empId == item; });
                        let lstEmpLicenseClassificationDto = _.find(data.lstEmpLicenseClassificationDto, function(o) { return o.empID == item; });
                        let lstEmpBase = _.find(data.lstEmpBase, function(o) { return o.empId == item; });
                    
                        
                        self.items.push(new ItemModel(lstEmpInforATR.empID, lstEmpBase.empCode.concat(lstEmpBase.empName) , lstEmpTeamInforDto.scheduleTeamName, lstEmpRankInforDto.rankName,
                            lstEmpLicenseClassificationDto.licenseClassification,
                            lstEmpInforATR.positionName,
                            lstEmpInforATR.classificationName));
                    });
                    dfd.resolve();
                });

                return dfd.promise();
            }

            createKey(sortName: string): string {
                let key = "";
                
                    if(sortName == nts.uk.resource.getText('KSU001_4048')){
                        key = 'description';
                        }else if(sortName == nts.uk.resource.getText('KSU001_4049')){
                        key = 'other1';
                        }else if(sortName == nts.uk.resource.getText('KSU001_4050')){
                        key = 'other2';
                        }else if(sortName == nts.uk.resource.getText('Com_Jobtitle')){
                        key = 'other3';
                        }else if(sortName == nts.uk.resource.getText('Com_Class')){
                        key = 'other4';
                        }
                return key;
            }
            cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }

        }
    }
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        other3: string;
        other4: string;

        constructor(code: string, name: string, description: string, other1: string, other2: string, other3: string, other4: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2;
            this.other3 = other3;
            this.other4 = other4;
        }
    }
}