module nts.uk.hr.view.jhc002.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        selectedHistId: KnockoutObservable<any>;
        itemList: any;
        masterTypelist: KnockoutObservableArray<ItemModel>;
        level: KnockoutObservableArray<number>;
        levelNumber: KnockoutObservable<number>;
        careerClass: KnockoutObservableArray<any>;

        requirementType: KnockoutObservableArray<any>;
        yearType: KnockoutObservableArray<any>;

        careerClassRole1: KnockoutObservable<any>;
        careerClassRole2: KnockoutObservable<any>;
        careerClassRole3: KnockoutObservable<any>;
        careerClassRole4: KnockoutObservable<any>;
        careerClassRole5: KnockoutObservable<any>;
        careerClassRole6: KnockoutObservable<any>;
        careerClassRole7: KnockoutObservable<any>;
        careerClassRole8: KnockoutObservable<any>;
        careerClassRole9: KnockoutObservable<any>;
        careerClassRole10: KnockoutObservable<any>;

        careerOrderList: KnockoutObservableArray<ScreenItem>;
        careerRequirementList: KnockoutObservableArray<ScreenItem>;
        career: KnockoutObservableArray<Career>;
        startDate: string;

        datatransfer: any;
        params: any;

        constructor() {
            var self = this;
            
            self.params = getShared("DataShareCareerToBScreen") || { undefined };
            //table 
            self.itemList = [];
            $("#fixed-table").ntsFixedTable({ height: 197, width: 780 });
            $("#fixed-table2").ntsFixedTable({ height: 294, width: 780 });
            $("#fixed-table3").ntsFixedTable({ height: 272, width: 311 });
            var div1 = $("#fixed-table").closest(".nts-fixed-body-container");
            div1.css("overflow-x", "hidden");
            var div2 = $("#fixed-table2").closest(".nts-fixed-body-container");
            var div3 = $("#fixed-table3").closest(".nts-fixed-body-container");
            div3.css("overflow", "hidden");
            div2.on("scroll", function() {
                div1[0].scrollLeft = div2[0].scrollLeft;
                div3[0].scrollTop = div2[0].scrollTop;
            });

            //          2019/3/11
            //          プロトタイプの製造時は、以下のリストをOutputとする。
            //          Khi code prototype se Out put list duoi 
            self.masterTypelist = ko.observableArray([
                new ItemModel('M00002', '職場マスタ'),
                new ItemModel('M00003', '雇用マスタ'),
                new ItemModel('M00004', '分類マスタ1'),
                new ItemModel('M00005', '職位マスタ')
            ]);

            //set width for table
            self.levelNumber = ko.observable(0);
            self.levelNumber.subscribe(function(newValue) {
                let width = newValue * 260;
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[2].style.width = width + "px";

                document.getElementsByClassName("fixed-table")[2].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[2].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[2].style.width = width + "px";
                
                document.getElementsByClassName("nts-fixed-body-container")[4].style.width = width + "px";
            });

            self.requirementType = ko.observable(__viewContext.enums.RequirementType);
            self.yearType = ko.observable(__viewContext.enums.YearType);

            self.careerClass = ko.observableArray([]);
            self.level = ko.observableArray([]);
            self.career = ko.observableArray([]);

            self.careerClassRole1 = ko.observable('');
            self.careerClassRole2 = ko.observable('');
            self.careerClassRole3 = ko.observable('');
            self.careerClassRole4 = ko.observable('');
            self.careerClassRole5 = ko.observable('');
            self.careerClassRole6 = ko.observable('');
            self.careerClassRole7 = ko.observable('');
            self.careerClassRole8 = ko.observable('');
            self.careerClassRole9 = ko.observable('');
            self.careerClassRole10 = ko.observable('');

            self.careerOrderList = ko.observableArray([]);
            self.careerRequirementList = ko.observableArray([]);
            self.datatransfer = null;
            self.startDate = '';

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            if(self.params.careerTypeId != undefined){
                self.datatransfer = self.params;
                self.startDate = self.params.startDate;
                //console.log(self.params);
                self.careerClass(self.params.careerClass);
                self.itemList = _.orderBy(_.filter(self.params.career, ['careerTypeItem', self.params.careerTypeId]), ['careerLevel'], ['asc']);
                //console.log(self.itemList);
                self.checksShowLever(self.itemList);
                self.addDefaultCareer();
                //console.log(self.careerOrderList());
                self.careerRequirementList(_.orderBy(self.careerRequirementList(), ['displayNumber'], ['asc']));
                dfd.resolve();
                block.clear();
            }else{
                nts.uk.request.jump("hr", "/view/jhc/002/a/index.xhtml");
                block.clear();
            }
            return dfd.promise();
        }

        public save(): void {
            let self = this;
            let checkData = true;
            let result = [];
            if(self.validateData()){
                _.forEach(self.career(), function(value) {
                    if(value.validate() == false){
                        checkData = false;
                        return false;    
                    }
                });
                if(checkData){
                    _.forEach(self.career(), function(value) {
                        let data = value.collectData();
                        if (data.length > 0) {
                            result = _.concat(result,data);
                        }
                    });
                    //console.log(result);
                    _.forEach(self.level(), function(value) {
                        let careerClassRole = '';
                        if(value == 1){
                            careerClassRole = self.careerClassRole1();
                        }else if(value == 2){
                            careerClassRole = self.careerClassRole2();
                        }else if(value == 3){
                            careerClassRole = self.careerClassRole3();
                        }else if(value == 4){
                            careerClassRole = self.careerClassRole4();
                        }else if(value == 5){
                            careerClassRole = self.careerClassRole5();
                        }else if(value == 6){
                            careerClassRole = self.careerClassRole6();
                        }else if(value == 7){
                            careerClassRole = self.careerClassRole7();
                        }else if(value == 8){
                            careerClassRole = self.careerClassRole8();
                        }else if(value == 9){
                            careerClassRole = self.careerClassRole9();
                        }else if(value == 10){
                            careerClassRole = self.careerClassRole10();
                        }
                        let index = _.findIndex(self.datatransfer.career,  { 'careerLevel': value, 'careerTypeItem': self.datatransfer.careerTypeId });
                        self.datatransfer.career[index].careerClassRole = careerClassRole;
                        self.datatransfer.career[index].careerRequirementList = _.filter(result, { 'lever': value});
                    });
                    block.clear();
                    nts.uk.request.jump("hr", "/view/jhc/002/a/index.xhtml", self.datatransfer);
                }
            }
        }
        
        public setRequirementType(displayNumber: number, requirementType: number): void {
            let self = this;
            let careerMasters = _.find(self.career(), {'displayNumber': displayNumber});
            careerMasters.setRequirementType(requirementType);
            self.validateData();
        }
        
        public setYearType(displayNumber: number, yearType: any): void {
            let self = this;
            let careerMasters = _.find(self.career(), {'displayNumber': displayNumber});
            careerMasters.setYearType(yearType);
            self.validateData();
        }
        
        public setMasterType(displayNumber: number, masterType: any): void {
            let self = this;
            let careerMasters = _.find(self.career(), {'displayNumber': displayNumber});
            careerMasters.removeListMasterItem();
            careerMasters.setMasterType(masterType);
            self.validateData();
        }
        
        public getValueAfterChangeMasterType(displayNumber: number): number {
            let self = this;
            let careerMasters = _.find(self.career(), {'displayNumber': displayNumber});
            return careerMasters.getModelInfor().masterType();
        }
        public getValueRequirementTypeAfterChangeMasterType(displayNumber: number): number {
            let self = this;
            let careerMasters = _.find(self.career(), {'displayNumber': displayNumber});
            return careerMasters.getModelInfor().requirementType();
        }
        
        public checkExitMasterItemList(displayNumber: number): boolean {
            let self = this;
            let result = false;
            let tg = _.filter(self.career(), { 'displayNumber': displayNumber});
            _.forEach(tg, function(value) {
                result = value.checkExitMasterItemList();
                if(result){
                    return false;    
                }
            });
            return result;
        }
        
        public checkExitDataWhenChangeRequirementType(displayNumber: number): boolean {
            let self = this;
            let result = false;
            let tg = _.filter(self.career(), { 'displayNumber': displayNumber});
            _.forEach(tg, function(value) {
                result = value.checkExitData();
                if(result){
                    return false;    
                }
            });
            return result;
        }
        
        public validateData(): boolean {
            let self = this;
            let yearTypeListCheck = [];
            let masterTypeListCheck = [];
            let result = true;
            _.forEach(self.careerOrderList(), function(value) {
                if (value.requirementType() == 1 && value.yearType() != null) {
                    if (!(_.includes(yearTypeListCheck, value.yearType()))) {
                        yearTypeListCheck.push(value.yearType());
                    } else {
                        nts.uk.ui.dialog.error({ messageId: 'MsgJ_46' });
                        result = false;
                        return false;
                    }
                } else if (value.requirementType() == 2 && value.masterType() != null) {
                    if (!(_.includes(masterTypeListCheck, value.masterType()))) {
                        masterTypeListCheck.push(value.masterType());
                    } else {
                        nts.uk.ui.dialog.error({ messageId: 'MsgJ_47' });
                        result = false;
                        return false;
                    }
                }
            });
            return result;
        }

        private addDefaultCareer(): void {
            let self = this;
            for (let i = 1; i < 7; i++) {
                _.forEach(self.level(), function(value) {
                    let careerRequirementValueRefer = _.find(self.careerRequirementList(), { 'displayNumber': i});
                    let careerRequirementDefault = {
                        displayNumber: i,
                        requirementType: careerRequirementValueRefer != undefined ? careerRequirementValueRefer.requirementType: 1,
                        yearType: careerRequirementValueRefer != undefined ? careerRequirementValueRefer.yearType: '',
                        yearMinimumNumber: '',
                        yearStandardNumber: '',
                        masterType: careerRequirementValueRefer != undefined ? careerRequirementValueRefer.masterType: '',
                        masterItemList: [],
                        inputRequirement: '',
                    }
                    if (_.find(self.careerRequirementList(), { 'displayNumber': i, 'lever': value }) == undefined) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirementDefault, value, null));
                    }
                });
                self.careerOrderList.push(new ScreenItem(ko.toJS(_.find(self.careerRequirementList(), { 'displayNumber': i })), i, true ));
                self.career.push(new Career(_.filter(self.careerRequirementList(), { 'displayNumber': i }), i));
            }
        }
        
        public openDiaLogMasterItem(selected: any): void {
            let self = this;
            let selectedCodes = [];
            let masterItemId = [];
            _.forEach(selected.masterItemList(), function(value) {
                selectedCodes.push(value.masterItemCd);
                if(value.masterItemId != null) masterItemId.push(value.masterItemId);;
            });
            let param = { isMultiple: true, 
                            showNoSelection: false, 
                            selectedCodes: selectedCodes, 
                            masterItemId: masterItemId, 
                            baseDate: self.startDate, 
                            isShowBaseDate: true, 
                            isrestrictionOfReferenceRange: false,
                            selectedSystemType: 1 // 1 : 個人情報 , 2 : 就業 , 3 :給与 , 4 :人事 ,  5 : 管理者
                         }
            let displayNumber = selected.displayNumber;
            let masterType = _.find(self.careerOrderList(), { 'displayNumber': displayNumber }).masterType();
            let careerMaster = _.find(self.career(), { 'displayNumber': displayNumber });
            if (masterType == 'M00002') {
                param.selectedCodes = masterItemId;
                nts.uk.ui.windows.setShared('inputCDL008', param);
                nts.uk.ui.windows.sub.modal('hr', '/view/jdl/0110/a/index.xhtml').onClosed(function() {
                    let data = getShared('outputCDL008');
                    if (data) {
                      careerMaster.getCareerbyLever(selected.lever)().setNameMasterItemList(data);
                    }
                });
            } else if (masterType == 'M00003') {
                nts.uk.ui.windows.setShared('CDL002Params', param);
                nts.uk.ui.windows.sub.modal('hr', '/view/jdl/0080/a/index.xhtml').onClosed(function() {
                    let data = getShared('CDL002Output');
                    if (data) {
                        careerMaster.getCareerbyLever(selected.lever)().setNameMasterItemList(data);
                    }
                });
            } else if (masterType == 'M00004') {
                nts.uk.ui.windows.setShared('inputCDL003', param);
                nts.uk.ui.windows.sub.modal('hr', '/view/jdl/0090/a/index.xhtml').onClosed(function() {
                    let data = getShared('outputCDL003');
                    if (data) {
                        careerMaster.getCareerbyLever(selected.lever)().setNameMasterItemList(data);
                    }
                });
                nts.uk.ui.windows.getShared
            } else if (masterType == 'M00005') {
                nts.uk.ui.windows.setShared('inputCDL004', param);
                nts.uk.ui.windows.sub.modal('hr', '/view/jdl/0100/a/index.xhtml').onClosed(function() {
                    let data = getShared('outputCDL004');
                    if (data) {
                        careerMaster.getCareerbyLever(selected.lever)().setNameMasterItemList(data);
                    }
                });
            }
        }
        
        public backTopAScreen(): void {
            let self = this;
            nts.uk.request.jump("hr", "/view/jhc/002/a/index.xhtml", self.datatransfer);
        }

        private checksShowLever(selected: any): void {
            let self = this;
            self.levelNumber(selected.length);
            _.forEach(selected, function(value) {
                if (value.careerLevel == 1) {
                    self.careerClassRole1(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 2) {
                    self.careerClassRole2(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 3) {
                    self.careerClassRole3(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 4) {
                    self.careerClassRole4(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 5) {
                    self.careerClassRole5(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 6) {
                    self.careerClassRole6(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 7) {
                    self.careerClassRole7(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 8) {
                    self.careerClassRole8(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 9) {
                    self.careerClassRole9(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                } else if (value.careerLevel == 10) {
                    self.careerClassRole10(_.find(self.itemList, { 'careerLevel': value.careerLevel }).careerClassRole);
                    _.forEach(value.careerRequirementList, function(careerRequirement) {
                        self.careerRequirementList.push(new ScreenItem(careerRequirement, value.careerLevel, null));
                    });
                }
                self.level.push(value.careerLevel);
            });
        }
    }

    class ScreenItem {
        comfirmChangeMasterType: boolean;
        lever: number;
        displayNumber: number;
        requirementType: KnockoutObservable<number>;
        yearType: KnockoutObservable<string>;
        yearMinimumNumber: KnockoutObservable<number>;
        yearStandardNumber: KnockoutObservable<number>;
        masterType: KnockoutObservable<string>;
        masterItemList: KnockoutObservableArray<MasterItem>;
        inputRequirement: KnockoutObservable<string>;
        constructor(obj: any, careerLevel: number, comfirmChangeMasterType: any) {
            var self = this;
            self.comfirmChangeMasterType = comfirmChangeMasterType; 
            self.lever = careerLevel;
            self.displayNumber = obj.displayNumber;
            self.requirementType = ko.observable(obj.requirementType);
            self.yearType = ko.observable(obj.yearRequirement == null ? (obj.yearType == undefined ? '' : obj.yearType) : obj.yearRequirement.yearType);
            self.yearMinimumNumber = ko.observable(obj.yearRequirement == null ? (obj.yearMinimumNumber == undefined ? '' : obj.yearMinimumNumber) : obj.yearRequirement.yearMinimumNumber);
            self.yearStandardNumber = ko.observable(obj.yearRequirement == null ? (obj.yearStandardNumber == undefined ? '' : obj.yearStandardNumber) : obj.yearRequirement.yearStandardNumber);
            self.masterType = ko.observable(obj.masterRequirement == null ? (obj.masterType == undefined ? '' : obj.masterType) : obj.masterRequirement.masterType);
            self.masterItemList = ko.observableArray(obj.masterRequirement == null ? (obj.masterItemList == undefined ? [] : obj.masterItemList) : obj.masterRequirement.masterItemList);
            self.inputRequirement = ko.observable(obj.inputRequirement);
            self.masterType.subscribe(function(newValue) {
                if (newValue != null && self.comfirmChangeMasterType == true) {
                    if(__viewContext.vm.checkExitMasterItemList(self.displayNumber)){
                        nts.uk.ui.dialog.confirmProceed({ messageId: "MsgJ_52" }).ifYes(() => {
                            __viewContext.vm.setMasterType(self.displayNumber, newValue);
                        }).ifNo(() => {
                            self.comfirmChangeMasterType = false;
                            self.masterType(__viewContext.vm.getValueAfterChangeMasterType(self.displayNumber));
                        });                        
                    }else{
                        __viewContext.vm.setMasterType(self.displayNumber, newValue);    
                    }
                }else if(self.comfirmChangeMasterType == false){
                    self.comfirmChangeMasterType = true;
                }
            });
            self.requirementType.subscribe(function(newValue) {
                if (newValue != null && self.comfirmChangeMasterType == true) {
                    if(__viewContext.vm.checkExitDataWhenChangeRequirementType(self.displayNumber)){
                        nts.uk.ui.dialog.confirmProceed({ messageId: "MsgJ_53" }).ifYes(() => {
                            __viewContext.vm.setRequirementType(self.displayNumber, parseInt(newValue));
                        }).ifNo(() => {
                            self.comfirmChangeMasterType = false;
                            self.requirementType(__viewContext.vm.getValueRequirementTypeAfterChangeMasterType(self.displayNumber));
                        });                        
                    }else{
                        __viewContext.vm.setRequirementType(self.displayNumber, parseInt(newValue));    
                    }
                }else if(self.comfirmChangeMasterType == false){
                    self.comfirmChangeMasterType = true;
                }
            });
            self.yearType.subscribe(function(newValue) {
                if (newValue != null && self.comfirmChangeMasterType == true) {
                    __viewContext.vm.setYearType(self.displayNumber, newValue);
                }
            });
            self.yearMinimumNumber.subscribe(function(newValue) {
                if (newValue != '' && self.yearStandardNumber() != '' && parseInt(newValue) > parseInt(self.yearStandardNumber())) {
                    nts.uk.ui.dialog.error({ messageId: 'MsgJ_51' });
                }
            });
            self.yearStandardNumber.subscribe(function(newValue) {
                if (newValue != '' && self.yearMinimumNumber() != '' && parseInt(newValue) < parseInt(self.yearMinimumNumber())) {
                    nts.uk.ui.dialog.error({ messageId: 'MsgJ_51' });
                }
            });
        }
        public validate(): boolean{
            let self = this;
            if (self.requirementType() == 1 && parseInt(self.yearMinimumNumber()) > parseInt(self.yearStandardNumber())) {
                nts.uk.ui.dialog.error({ messageId: 'MsgJ_51' });
                return false;
            }
            return true;
        }
        
        public collectData(): any{
            let self = this;
            let result = {
                lever: self.lever,
                displayNumber: self.displayNumber,
                inputRequirement: null,
                masterRequirement: null,
                requirementType: self.requirementType(),
                yearRequirement: null
            }
            if(self.requirementType()==1 && self.yearType() != '' && self.yearMinimumNumber() != '' && self.yearStandardNumber() != ''){
                let yearRequirement = {yearType: self.yearType(), yearMinimumNumber: self.yearMinimumNumber(), yearStandardNumber: self.yearStandardNumber()}
                result.yearRequirement = yearRequirement;
                return result;
            }else if(self.requirementType()==2 && self.masterType() != '' && self.masterItemList().length != 0){
                let masterRequirement = {masterType: self.masterType(), masterItemList: self.masterItemList()}
                result.masterRequirement = masterRequirement;
                return result;
            }else if(self.requirementType()==3 && self.inputRequirement() != ''){
                result.inputRequirement = self.inputRequirement();
                return result;
            }else{
                return null;    
            }
        }

        public getNameMasterItemList(): string {
            let self = this;
            let nameMasterItemList = '';
            _.forEach(self.masterItemList(), function(value) {
                nameMasterItemList = nameMasterItemList +(nameMasterItemList!=''?' + ':'')+ value.masterItemName;
            });
            return nameMasterItemList;
        }

        public setNameMasterItemList(obj: any): void {
            let self = this;
            let masterItemDialogResult = [];
            _.forEach(obj, function(value) {
                masterItemDialogResult.push(new MasterItem(value));
            });
            this.masterItemList(masterItemDialogResult);
        }
        
        public setYearType(yearType: any): void {
            this.yearType(yearType);
        }
        
        public setMasterType(masterType: any): void {
            this.masterType(masterType);
        }
        public getYearType(): any {
            return this.yearType();
        }
        
        public removeDataByRequirementType(): void{
            let self = this;
            if(self.requirementType() == 1){
                self.yearMinimumNumber('');
                self.yearStandardNumber('');
            }else if(self.requirementType() == 2){
                self.masterItemList([]);
            }else if(self.requirementType() == 3){
                self.inputRequirement('');
            }
        }
        
        public checkExitData(): boolean{
            let self = this;
            if(self.requirementType() == 1 && (self.yearMinimumNumber() != '' || self.yearStandardNumber() != '')){
                return true;
            }else if(self.requirementType() == 2 && self.masterItemList().length > 0){
                return true;
            }else if(self.requirementType() == 3 && self.inputRequirement() != ''){
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
    
    class MasterItem {
        masterItemId: string;
        masterItemCd: string;
        masterItemName: string;
        constructor(obj: any) {
            this.masterItemId = obj.masterItemId != undefined? obj.masterItemId: (obj.id != undefined ? obj.id : (obj.workplaceId != undefined ? obj.workplaceId :null));
            this.masterItemCd = obj.masterItemCd == undefined? obj.code: obj.masterItemCd;
            this.masterItemName = obj.masterItemName == undefined? obj.name: obj.masterItemName;
        }
    }
    
    class Career {
        displayNumber: number;
        lever1: KnockoutObservable<ScreenItem>;
        lever2: KnockoutObservable<ScreenItem>;
        lever3: KnockoutObservable<ScreenItem>;
        lever4: KnockoutObservable<ScreenItem>;
        lever5: KnockoutObservable<ScreenItem>;
        lever6: KnockoutObservable<ScreenItem>;
        lever7: KnockoutObservable<ScreenItem>;
        lever8: KnockoutObservable<ScreenItem>;
        lever9: KnockoutObservable<ScreenItem>;
        lever10: KnockoutObservable<ScreenItem>;
        constructor(obj: any, displayNumber: number) {
            var self = this;
            self.displayNumber = displayNumber;
            _.forEach(obj, function(value) {
                if (value.lever == 1) {
                    self.lever1 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 2) {
                    self.lever2 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 3) {
                    self.lever3 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 4) {
                    self.lever4 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 5) {
                    self.lever5 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 6) {
                    self.lever6 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 7) {
                    self.lever7 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 8) {
                    self.lever8 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 9) {
                    self.lever9 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                } else if (value.lever == 10) {
                    self.lever10 = ko.observable(new ScreenItem(ko.toJS(value), value.lever, null));
                }
            });
        }
        public getCareerbyLever(lever: number): ScreenItem {
            let self = this;
            if (lever == 1) {
                return self.lever1;
            } else if (lever == 2) {
                return self.lever2;
            } else if (lever == 3) {
                return self.lever3;
            } else if (lever == 4) {
                return self.lever4;
            } else if (lever == 5) {
                return self.lever5;
            } else if (lever == 6) {
                return self.lever6;
            } else if (lever == 7) {
                return self.lever7;
            } else if (lever == 8) {
                return self.lever8;
            } else if (lever == 9) {
                return self.lever9;
            } else if (lever == 10) {
                return self.lever10;
            }
        }
        
        public removeListMasterItem(): void {
            let self = this;
            if (self.lever1 != undefined) {
                self.lever1().setNameMasterItemList('');
            }
            if (self.lever2 != undefined) {
                self.lever2().setNameMasterItemList('');
            }
            if (self.lever3 != undefined) {
                self.lever3().setNameMasterItemList('');
            }
            if (self.lever4 != undefined) {
                self.lever4().setNameMasterItemList('');
            } 
            if (self.lever5 != undefined) {
                self.lever5().setNameMasterItemList('');
            } 
            if (self.lever6 != undefined) {
                self.lever6().setNameMasterItemList('');
            }
            if (self.lever7 != undefined) {
                self.lever7().setNameMasterItemList('');
            }
            if (self.lever8 != undefined) {
                self.lever8().setNameMasterItemList('');
            }
            if (self.lever9 != undefined) {
                self.lever9().setNameMasterItemList('');
            } 
            if (self.lever10 != undefined) {
                self.lever10().setNameMasterItemList('');
            } 
        }
        public setYearType(yearType: any): void {
            let self = this;
            if (self.lever1 != undefined) {
                self.lever1().setYearType(yearType);
            }
            if (self.lever2 != undefined) {
                self.lever2().setYearType(yearType);
            }
            if (self.lever3 != undefined) {
                self.lever3().setYearType(yearType);
            }
            if (self.lever4 != undefined) {
                self.lever4().setYearType(yearType);
            } 
            if (self.lever5 != undefined) {
                self.lever5().setYearType(yearType);
            } 
            if (self.lever6 != undefined) {
                self.lever6().setYearType(yearType);
            }
            if (self.lever7 != undefined) {
                self.lever7().setYearType(yearType);
            }
            if (self.lever8 != undefined) {
                self.lever8().setYearType(yearType);
            }
            if (self.lever9 != undefined) {
                self.lever9().setYearType(yearType);
            } 
            if (self.lever10 != undefined) {
                self.lever10().setYearType(yearType);
            } 
        }
        
        public setMasterType(masterType: any): void {
            let self = this;
            if (self.lever1 != undefined) {
                self.lever1().setMasterType(masterType);
            }
            if (self.lever2 != undefined) {
                self.lever2().setMasterType(masterType);
            }
            if (self.lever3 != undefined) {
                self.lever3().setMasterType(masterType);
            }
            if (self.lever4 != undefined) {
                self.lever4().setMasterType(masterType);
            } 
            if (self.lever5 != undefined) {
                self.lever5().setMasterType(masterType);
            } 
            if (self.lever6 != undefined) {
                self.lever6().setMasterType(masterType);
            }
            if (self.lever7 != undefined) {
                self.lever7().setMasterType(masterType);
            }
            if (self.lever8 != undefined) {
                self.lever8().setMasterType(masterType);
            }
            if (self.lever9 != undefined) {
                self.lever9().setMasterType(masterType);
            } 
            if (self.lever10 != undefined) {
                self.lever10().setMasterType(masterType);
            } 
        }
        
        public checkExitMasterItemList(): boolean{
            let self = this;
            if (self.lever1 != undefined && self.lever1().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever2 != undefined && self.lever2().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever3 != undefined && self.lever3().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever4 != undefined && self.lever4().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever5 != undefined && self.lever5().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever6 != undefined && self.lever6().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever7 != undefined && self.lever7().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever8 != undefined && self.lever8().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever9 != undefined && self.lever9().getNameMasterItemList() != '') {
                return true;
            }else if (self.lever10 != undefined && self.lever10().getNameMasterItemList() != '') {
                return true;
            } 
            return false;
        }
        
        public checkExitData(): boolean{
            let self = this;
            if (self.lever1 != undefined && self.lever1().checkExitData()) {
                return true;
            }
            if (self.lever2 != undefined && self.lever2().checkExitData()) {
                return true;
            }
            if (self.lever3 != undefined && self.lever3().checkExitData()) {
                return true;
            }
            if (self.lever4 != undefined && self.lever4().checkExitData()) {
                return true;
            }
            if (self.lever5 != undefined && self.lever5().checkExitData()) {
                return true;
            }
            if (self.lever6 != undefined && self.lever6().checkExitData()) {
                return true;
            }
            if (self.lever7 != undefined && self.lever7().checkExitData()) {
                return true;
            }
            if (self.lever8 != undefined && self.lever8().checkExitData()) {
                return true;
            }
            if (self.lever9 != undefined && self.lever9().checkExitData()) {
                return true;
            }
            if (self.lever10 != undefined && self.lever10().checkExitData()) {
                return true;
            }
            return false;
        }
        
        public setRequirementType(requirementType: any): void {
            let self = this;
            if (self.lever1 != undefined) {
                self.lever1().removeDataByRequirementType();
                self.lever1().requirementType(requirementType);
            }
            if (self.lever2 != undefined) {
                self.lever2().removeDataByRequirementType();
                self.lever2().requirementType(requirementType);
            }
            if (self.lever3 != undefined) {
                self.lever3().removeDataByRequirementType();
                self.lever3().requirementType(requirementType);
            }
            if (self.lever4 != undefined) {
                self.lever4().removeDataByRequirementType();
                self.lever4().requirementType(requirementType);
            } 
            if (self.lever5 != undefined) {
                self.lever5().removeDataByRequirementType();
                self.lever5().requirementType(requirementType);
            } 
            if (self.lever6 != undefined) {
                self.lever6().removeDataByRequirementType();
                self.lever6().requirementType(requirementType);
            }
            if (self.lever7 != undefined) {
                self.lever7().removeDataByRequirementType();
                self.lever7().requirementType(requirementType);
            }
            if (self.lever8 != undefined) {
                self.lever8().removeDataByRequirementType();
                self.lever8().requirementType(requirementType);
            }
            if (self.lever9 != undefined) {
                self.lever9().removeDataByRequirementType();
                self.lever9().requirementType(requirementType);
            } 
            if (self.lever10 != undefined) {
                self.lever10().removeDataByRequirementType();
                self.lever10().requirementType(requirementType);
            } 
        }
        
        public getModelInfor(): ScreenItem {
            let self = this;
            if (self.lever1 != undefined) {
                return self.lever1();
            }else if (self.lever2 != undefined) {
                return self.lever2();
            }else if (self.lever3 != undefined) {
                return self.lever3();
            }else if (self.lever4 != undefined) {
                return self.lever4();
            }else if (self.lever5 != undefined) {
                return self.lever5();
            }else if (self.lever6 != undefined) {
                return self.lever6();
            }else if (self.lever7 != undefined) {
                return self.lever7();
            }else if (self.lever8 != undefined) {
                return self.lever8();
            }else if (self.lever9 != undefined) {
                return self.lever9();
            }else if (self.lever10 != undefined) {
                return self.lever10();
            }  
        }
        public validate(): boolean {
            let self = this;
            let result = true;
            if (self.lever1 != undefined) {
                result = self.lever1().validate();
            }
            if (result && self.lever2 != undefined) {
                result = self.lever2().validate();
            }
            if (result && self.lever3 != undefined) {
                result = self.lever3().validate();
            }
            if (result && self.lever4 != undefined) {
                result = self.lever4().validate();
            }
            if (result && self.lever5 != undefined) {
                result = self.lever5().validate();
            }
            if (result && self.lever6 != undefined) {
                result = self.lever6().validate();
            }
            if (result && self.lever7 != undefined) {
                result = self.lever7().validate();
            }
            if (result && self.lever8 != undefined) {
                result = self.lever8().validate();
            }
            if (result && self.lever9 != undefined) {
                result = self.lever9().validate();
            }
            if (result && self.lever10 != undefined) {
                result = self.lever10().validate();
            }
            return result;
        }
        
        public collectData (): any {
            let self =  this;
            let result = [];
            if (self.lever1 != undefined) {
                let data = self.lever1().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever2 != undefined) {
                let data = self.lever2().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever3 != undefined) {
                let data = self.lever3().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever4 != undefined) {
                let data = self.lever4().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever5 != undefined) {
                let data = self.lever5().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever6 != undefined) {
                let data = self.lever6().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever7 != undefined) {
                let data = self.lever7().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever8 != undefined) {
                let data = self.lever8().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever9 != undefined) {
                let data = self.lever9().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            if (self.lever10 != undefined) {
                let data = self.lever10().collectData();
                if(data != null){
                    result.push(data);    
                }
            }
            return result;
        }
    }

}
