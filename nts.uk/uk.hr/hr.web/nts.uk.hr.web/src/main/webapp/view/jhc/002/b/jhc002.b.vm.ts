module nts.uk.hr.view.jhc002.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        selectedHistId: KnockoutObservable<any>;
        itemList: KnockoutObservableArray<ScreenItem>;
        masterTypelist: KnockoutObservableArray<ItemModel>;
        levelNumber: KnockoutObservable<number>;
        careerClass:  KnockoutObservableArray<any>;
        
        requirementType: KnockoutObservableArray<any>;
        yearType: KnockoutObservableArray<any>;
        
        showLevel1: KnockoutObservable<boolean>;
        showLevel2: KnockoutObservable<boolean>;
        showLevel3: KnockoutObservable<boolean>;
        showLevel4: KnockoutObservable<boolean>;
        showLevel5: KnockoutObservable<boolean>;
        showLevel6: KnockoutObservable<boolean>;
        showLevel7: KnockoutObservable<boolean>;
        showLevel8: KnockoutObservable<boolean>;
        showLevel9: KnockoutObservable<boolean>;
        showLevel10: KnockoutObservable<boolean>;
        
        nameLevel1: KnockoutObservable<any>;
        nameLevel2: KnockoutObservable<any>;
        nameLevel3: KnockoutObservable<any>;
        nameLevel4: KnockoutObservable<any>;
        nameLevel5: KnockoutObservable<any>;
        nameLevel6: KnockoutObservable<any>;
        nameLevel7: KnockoutObservable<any>;
        nameLevel8: KnockoutObservable<any>;
        nameLevel9: KnockoutObservable<any>;
        nameLevel10: KnockoutObservable<any>;
        
        careerClassRoleLevel1: KnockoutObservable<any>;
        careerClassRoleLevel2: KnockoutObservable<any>;
        careerClassRoleLevel3: KnockoutObservable<any>;
        careerClassRoleLevel4: KnockoutObservable<any>;
        careerClassRoleLevel5: KnockoutObservable<any>;
        careerClassRoleLevel6: KnockoutObservable<any>;
        careerClassRoleLevel7: KnockoutObservable<any>;
        careerClassRoleLevel8: KnockoutObservable<any>;
        careerClassRoleLevel9: KnockoutObservable<any>;
        careerClassRoleLevel10: KnockoutObservable<any>;
        
        careerRequirementList1: KnockoutObservableArray<ScreenItem>;
        careerRequirementList2: KnockoutObservableArray<ScreenItem>;
        careerRequirementList3: KnockoutObservableArray<ScreenItem>;
        careerRequirementList4: KnockoutObservableArray<ScreenItem>;
        careerRequirementList5: KnockoutObservableArray<ScreenItem>;
        careerRequirementList6: KnockoutObservableArray<ScreenItem>;
        careerRequirementList7: KnockoutObservableArray<ScreenItem>;
        careerRequirementList8: KnockoutObservableArray<ScreenItem>;
        careerRequirementList9: KnockoutObservableArray<ScreenItem>;
        careerRequirementList10: KnockoutObservableArray<ScreenItem>;
        
        careerRequirementList: KnockoutObservableArray<ScreenItem>;
        
        nameLevel: KnockoutObservable<DataLever>;
        
        constructor() {
            var self = this;
            //table 
            self.itemList = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 197, width: 990 });
            $("#fixed-table2").ntsFixedTable({ height: 197, width: 990 });
//          2019/3/11
//          プロトタイプの製造時は、以下のリストをOutputとする。
//          Khi code prototype se Out put list duoi 
            self.masterTypelist = ko.observableArray([
                new ItemModel('M00002', '職場マスタ'),
                new ItemModel('M00003', '雇用マスタ'),
                new ItemModel('M00004', '分類マスタ1'),
                new ItemModel('M00005', '職位マスタ')
            ]);
            
            self.levelNumber = ko.observable(0);
            self.requirementType = ko.observable(__viewContext.enums.RequirementType);
            self.yearType = ko.observable(__viewContext.enums.YearType);
            
            //set width for table
            self.levelNumber.subscribe(function(newValue) {
                let width = newValue * 260;
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[1].style.width = width + "px";
                
                document.getElementsByClassName("fixed-table")[1].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[1].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[1].style.width = width + "px";
            });
            
            self.careerClass = ko.observableArray([]);
            
            self.showLevel1 = ko.observable(false);
            self.showLevel2 = ko.observable(false);
            self.showLevel3 = ko.observable(false);
            self.showLevel4 = ko.observable(false);
            self.showLevel5 = ko.observable(false);
            self.showLevel6 = ko.observable(false);
            self.showLevel7 = ko.observable(false);
            self.showLevel8 = ko.observable(false);
            self.showLevel9 = ko.observable(false);
            self.showLevel10 = ko.observable(false);
            
            self.nameLevel1 = ko.observable('');
            self.nameLevel2 = ko.observable('');
            self.nameLevel3 = ko.observable('');
            self.nameLevel4 = ko.observable('');
            self.nameLevel5 = ko.observable('');
            self.nameLevel6 = ko.observable('');
            self.nameLevel7 = ko.observable('');
            self.nameLevel8 = ko.observable('');
            self.nameLevel9 = ko.observable('');
            self.nameLevel10 = ko.observable('');
            
            self.careerClassRoleLevel1 = ko.observable('');
            self.careerClassRoleLevel2 = ko.observable('');
            self.careerClassRoleLevel3 = ko.observable('');
            self.careerClassRoleLevel4 = ko.observable('');
            self.careerClassRoleLevel5 = ko.observable('');
            self.careerClassRoleLevel6 = ko.observable('');
            self.careerClassRoleLevel7 = ko.observable('');
            self.careerClassRoleLevel8 = ko.observable('');
            self.careerClassRoleLevel9 = ko.observable('');
            self.careerClassRoleLevel10 = ko.observable('');
            
            self.careerRequirementList1 = ko.observableArray([]);
            self.careerRequirementList2 = ko.observableArray([]);
            self.careerRequirementList3 = ko.observableArray([]);
            self.careerRequirementList4 = ko.observableArray([]);
            self.careerRequirementList5 = ko.observableArray([]);
            self.careerRequirementList6 = ko.observableArray([]);
            self.careerRequirementList7 = ko.observableArray([]);
            self.careerRequirementList8 = ko.observableArray([]);
            self.careerRequirementList9 = ko.observableArray([]);
            self.careerRequirementList10 = ko.observableArray([]);
            
            self.careerRequirementList = ko.observableArray([]);

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            nts.uk.characteristics.restore("DataShareCareerToBScreen").done((obj) => { 
                console.log(obj);
                self.careerClass(obj.careerClass);
                let selected = _.orderBy(_.filter(obj.career, ['careerTypeItem', obj.careerTypeId]), ['careerLevel'], ['asc']);
                console.log(selected);
                self.checksShowLever(selected);
                self.addDefaultCareer();
                let param = {
                    careerTypeItem: obj.careerTypeId,
                    historyId: obj.historyId
                }
                new service.getCareerList(param).done(function(data: any) {
                    console.log(data);
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.error({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }).fail(() => {
                dfd.resolve();
                block.clear();
            });
            return dfd.promise();
        }
        public save():void{
            let self = this;
                
        }
        
        private checksShowLever(selected: any): void{
            let self = this;
            self.levelNumber(selected.length);    
            _.forEach(selected, function(value){
                if(value.careerLevel == 1){
                    self.showLevel1(true);
                    self.nameLevel1(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel1(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList1.push(new ScreenItem(careerRequirement, value.careerLevel));   
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 2) {
                    self.showLevel2(true);
                    self.nameLevel2(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel2(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList2.push(new ScreenItem(careerRequirement, value.careerLevel));
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        } 
                    });
                } else if (value.careerLevel == 3) {
                    self.showLevel3(true);
                    self.nameLevel3(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel3(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList3.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 4) {
                    self.showLevel4(true);
                    self.nameLevel4(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel4(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList4.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 5) {
                    self.showLevel5(true);
                    self.nameLevel5(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel5(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList5.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 6) {
                    self.showLevel6(true);
                    self.nameLevel6(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel6(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList6.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 7) {
                    self.showLevel7(true);
                    self.nameLevel7(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel7(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList7.push(new ScreenItem(careerRequirement, value.careerLevel));
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        } 
                    });
                } else if (value.careerLevel == 8) {
                    self.showLevel8(true);
                    self.nameLevel8(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel8(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList8.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 9) {
                    self.showLevel9(true);
                    self.nameLevel9(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel9(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList9.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                } else if (value.careerLevel == 10) {
                    self.showLevel10(true);
                    self.nameLevel10(_.find(self.careerClass(),{'id':value.careerClassItem}).name);
                    self.careerClassRoleLevel10(value.careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement){
                        self.careerRequirementList10.push(new ScreenItem(careerRequirement, value.careerLevel)); 
                        if(_.find(self.careerRequirementList(),{'displayNumber':careerRequirement.displayNumber}) == undefined){
                             self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel))   
                        }
                    });
                }    
            });
        }
        private addDefaultCareer(): void{
            let self = this;
            for(let i = 1; i < 7; i++){
                let careerRequirementDefault = {
                    displayNumber: i,
                    requirementType: '',
                    yearType: '',
                    yearMinimumNumber: '',
                    yearStandardNumber: '',
                    masterType: '',
                    masterItemList: [],
                    inputRequirement: '',
                }
                if(_.find(self.careerRequirementList1(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList1.push(new ScreenItem(careerRequirementDefault, 1));
                }
                if(_.find(self.careerRequirementList2(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList2.push(new ScreenItem(careerRequirementDefault, 2));
                }
                if(_.find(self.careerRequirementList3(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList3.push(new ScreenItem(careerRequirementDefault, 3));
                }
                if(_.find(self.careerRequirementList4(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList4.push(new ScreenItem(careerRequirementDefault, 4));
                }
                if(_.find(self.careerRequirementList5(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList5.push(new ScreenItem(careerRequirementDefault, 5));
                }
                if(_.find(self.careerRequirementList6(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList6.push(new ScreenItem(careerRequirementDefault, 6));
                }
                if(_.find(self.careerRequirementList7(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList7.push(new ScreenItem(careerRequirementDefault, 7));
                }
                if(_.find(self.careerRequirementList8(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList8.push(new ScreenItem(careerRequirementDefault, 8));
                }
                if(_.find(self.careerRequirementList9(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList9.push(new ScreenItem(careerRequirementDefault, 9));
                }
                if(_.find(self.careerRequirementList10(), {'displayNumber':i}) == undefined){
                    self.careerRequirementList10.push(new ScreenItem(careerRequirementDefault, 10));
                }
                if(_.find(self.careerRequirementList(),{'displayNumber':i}) == undefined){
                     self.careerRequirementList.push(new ScreenItem(careerRequirementDefault, 0))   
                }
            }
        }
    }

    class ScreenItem {
        lever: number;
        displayNumber: number;
        requirementType: number;
        yearType: string;
        yearMinimumNumber: string;
        yearStandardNumber: string;
        masterType: string;
        masterItemList: any;
        inputRequirement: string;
        constructor(obj: any, careerLevel: number) {
            var self = this;
            self.lever = careerLevel;
            self.displayNumber = obj.displayNumber;
            self.requirementType = obj.requirementType;
            self.yearType = obj.yearRequirement == null ? '': obj.yearRequirement.yearType;
            self.yearMinimumNumber = obj.yearRequirement == null ? '': obj.yearRequirement.yearMinimumNumber;
            self.yearStandardNumber = obj.yearRequirement == null ? '': obj.yearRequirement.yearStandardNumber;
            self.masterType = obj.masterRequirement == null ? '': obj.masterRequirement.masterType;
            self.masterItemList = obj.masterRequirement == null ? []: obj.masterRequirement.masterItemList;
            self.inputRequirement = obj.inputRequirement;
        }
        
        public validate(): boolean{
            let self = this;
            if(self.requirementType == 1 && self.yearType != '' && self.yearStandardNumber != '' && self.yearStandardNumber != ''){
                return true;
            }else if(self.requirementType == 2 && self.masterType != '' && self.masterItemList.length != 0){
                return true;
            }else if(self.requirementType == 3 && self.inputRequirement != ''){
                return true;
            }   
            return false;
        }
    }
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class DataLever{
        data1: string;
        data2: string;
        data3: string;
        data4: string;
        data5: string;
        data6: string;
        data7: string;
        data8: string;
        data9: string;
        data10: string;
        constructor() {} 
    }
    
}
