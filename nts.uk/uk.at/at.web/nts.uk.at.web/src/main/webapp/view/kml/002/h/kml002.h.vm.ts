module nts.uk.at.view.kml002.k {
    import getText = nts.uk.resource.getText;
    import setShare = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    const Paths = {
        GET_ESTIMATE_INFO_BY_CID:"screen/at/kml002/h/init",
        GET_DEFAULT_INFO:"screen/at/kml002/k/init",
        GET_ESTIMATE_INFO_BY_CID_AND_CODE:"screen/at/kml002/k/getEstimatedInfo",
        REGISTER_SETTING_CMP:"ctx/at/schedule/budget/initCompanyInfo/register",
        REGISTER_SETTING_EMP:"ctx/at/schedule/budget/employmentsetting/register",
        DELETE_SETTING_EMP:"ctx/at/schedule/budget/employmentsetting/remove"

    };
    @bean()
    class Kml002hViewModel extends ko.ViewModel {
        name: KnockoutObservable<string> = ko.observable('');

        itemHandling: KnockoutObservable<ItemHandling> = ko.observable(new ItemHandling());
        itemMonthly: KnockoutObservable<ItemMonthly> = ko.observable(new ItemMonthly());
        itemAnnual: KnockoutObservable<ItemAnnual> = ko.observable(new ItemAnnual());


        itemHandlingScreenK: KnockoutObservable<ItemHandling> = ko.observable();
        itemMonthlyScreenK: KnockoutObservable<ItemMonthly> = ko.observable(new ItemMonthly());
        itemAnnualScreenK: KnockoutObservable<ItemAnnual> = ko.observable(new ItemAnnual());
        isReloadScreenK: KnockoutObservable<boolean> = ko.observable(false);
        isUseageEmployment: KnockoutObservable<boolean> = ko.observable(true);

        currentScreen: any = null;
        

        listComponentOption: any;
        selectedCode: KnockoutObservable<string> = ko.observable('1');
        isShowAlreadySet: KnockoutObservable<boolean> = ko.observable(true);
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        isDialog: KnockoutObservable<boolean> = ko.observable(false);
        isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(false);
        isMultiSelect: KnockoutObservable<boolean> = ko.observable(false);
        employmentList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
        //Upgrade
        isDisplayClosureSelection: KnockoutObservable<boolean> = ko.observable(false);
        isDisplayFullClosureOption: KnockoutObservable<boolean> = ko.observable(false);
        closureSelectionType: KnockoutObservable<number>;
        selectClosureTypeList: KnockoutObservableArray<any>;
        listEmp: KnockoutObservableArray<EmpModel> = ko.observableArray<EmpModel>([]);
        
        constructor() {
            super();
            const self = this;      
            $('#screen-h').click(function(){
                self.loadData();
                // self.$window.size(660, 1050);
             }); 
            $('#screen-k').click(function(){
                self.loadDataScreenK();
            });       

            self.loadData();
           
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                maxRows: 12
            };

            self.selectedCode.subscribe((code) => {
                self.findDetail(code);
            }); 

            self.itemHandling().backgroundColor1.subscribe((val) => {                
                $('#colorpicker1').ntsError('clear');
                if (!val || val =='') {
                    $('#colorpicker1').ntsError('set', { messageId: 'MsgB_2', messageParams: [getText("KML002_132")]}); 
                }
            });     
            self.itemHandling().backgroundColor2.subscribe((val) => {               
                $('#colorpicker2').ntsError('clear');
                if (!val || val =='') {
                    $('#colorpicker2').ntsError('set', { messageId: 'MsgB_2', messageParams: [getText("KML002_134")]}); 
                }
            });  
            self.itemHandling().backgroundColor3.subscribe((val) => {                
                $('#colorpicker3').ntsError('clear');
                if (!val || val =='') {
                    $('#colorpicker3').ntsError('set', { messageId: 'MsgB_2', messageParams: [getText("KML002_135")]}); 
                }
            });  
            self.itemHandling().backgroundColor4.subscribe((val) => {                
                $('#colorpicker4').ntsError('clear');
                if (!val || val =='') {
                    $('#colorpicker4').ntsError('set', { messageId: 'MsgB_2', messageParams: [getText("KML002_136")]}); 
                }
            });  
            self.itemHandling().backgroundColor5.subscribe((val) => {                
                $('#colorpicker5').ntsError('clear');
                if (!val || val =='') {
                    $('#colorpicker5').ntsError('set', { messageId: 'MsgB_2', messageParams: [getText("KML002_137")]}); 
                }
            });  

            self.itemMonthly().amount1.subscribe((val) => {
                $('#month2').ntsError('clear');
                if(self.itemMonthly().amount2() < val){
                    $('#month2').ntsError('set', { messageId: 'MsgB_1', messageParams: [getText("KML002_153") + getText("KML002_140")]}); 
                }
            });
            self.itemMonthly().amount2.subscribe((val) => {
                $('#month2').ntsError('clear');
                if(self.itemMonthly().amount1() > val){
                    $('#month2').ntsError('set', { messageId: 'MsgB_1', messageParams: [getText("KML002_153") + getText("KML002_140")]}); 
                }
            });

            self.itemMonthly().amount3.subscribe((val) => {
                $('#month3').ntsError('clear');
                if(self.itemMonthly().amount2() > val){
                    $('#month3').ntsError('set', { messageId: 'MsgB_1', messageParams: [getText("KML002_153") + getText("KML002_141")]}); 
                }
            });

            self.itemMonthly().amount4.subscribe((val) => {
                $('#month4').ntsError('clear');
                if(self.itemMonthly().amount3() > val){
                    $('#month4').ntsError('set', { messageId: 'MsgB_1', messageParams: [getText("KML002_153") + getText("KML002_142")]}); 
                }
            });

            self.itemMonthly().amount5.subscribe((val) => {
                $('#month5').ntsError('clear');
                if(self.itemMonthly().amount4() > val){
                    $('#month5').ntsError('set', { messageId: 'MsgB_1', messageParams: [getText("KML002_153") + getText("KML002_143")]}); 
                }
            });
        }

        loadData(): void {
            const self = this;
            let dataHandling: Array<string> = [], dataMonthly: Array<string> = [], dataAnnual: Array<string> = [];
            let amountMonthly: string, amountAnnual: string, backgroundColor: string;
            self.$blockui("invisible");
            self.$ajax(Paths.GET_ESTIMATE_INFO_BY_CID).done((data: any) => {
                if (data) {
                    self.isUseageEmployment(data.useSetting);
                    for (let i = 1; i < 6; i++) {
                        if (i <= data.esimatedInfoDto.months.length && data.esimatedInfoDto.months[i - 1].frameNo == i) {
                            amountMonthly = data.esimatedInfoDto.months[i - 1].amount;
                        } else {
                            amountMonthly = '';
                        }
                        if (i <= data.esimatedInfoDto.annuals.length && data.esimatedInfoDto.annuals[i - 1].frameNo == i) {
                            amountAnnual = data.esimatedInfoDto.annuals[i - 1].amount;
                        } else {
                            amountAnnual = '';
                        }

                        if (i <= data.esimatedInfoDto.listHandlingByNo.length && data.esimatedInfoDto.listHandlingByNo[i - 1].frameNo == i) {
                            backgroundColor = '#' + data.esimatedInfoDto.listHandlingByNo[i - 1].backgroundColor;
                        } else {
                            backgroundColor = '#FFFFFF';
                        }

                        dataHandling.push(backgroundColor);
                        dataMonthly.push(amountMonthly);
                        dataAnnual.push(amountAnnual);
                    }
                }
                self.itemHandling(new ItemHandling(dataHandling));
                self.itemMonthly(new ItemMonthly(dataMonthly));
                self.itemAnnual(new ItemAnnual(dataAnnual));
            }).always(() => {
                self.$blockui("hide");
            });
        }
        
        registerMoneyCmp(): void {
            const self = this;
            let command: any = {}, handlings: Array<ItemHandlingModel> = [], months: Array<ItemAmountModel> = [],
                years: Array<ItemAmountModel> = [];

            if (self.validateAll()) {
                return;
            }
                       
            handlings.push({ "frameNo": 1, "backgroundColor": self.itemHandling().backgroundColor1() });
            handlings.push({ "frameNo": 2, "backgroundColor": self.itemHandling().backgroundColor2() });
            handlings.push({ "frameNo": 3, "backgroundColor": self.itemHandling().backgroundColor3() });
            handlings.push({ "frameNo": 4, "backgroundColor": self.itemHandling().backgroundColor4() });
            handlings.push({ "frameNo": 5, "backgroundColor": self.itemHandling().backgroundColor5() });

            if(self.itemMonthly().amount1()){
                months.push({ "frameNo": 1, "amount": parseInt(self.itemMonthly().amount1()) });
            }
            if(self.itemMonthly().amount2()){
                months.push({ "frameNo": 2, "amount": parseInt(self.itemMonthly().amount2()) });
            }
            if(self.itemMonthly().amount3()){
                months.push({ "frameNo": 3, "amount": parseInt(self.itemMonthly().amount3()) });
            }
            if(self.itemMonthly().amount4()){
                months.push({ "frameNo": 4, "amount": parseInt(self.itemMonthly().amount4()) });
            }
            if(self.itemMonthly().amount5()){
                months.push({ "frameNo": 5, "amount": parseInt(self.itemMonthly().amount5()) });
            }

            if(self.itemAnnual().amount1()){
                years.push({ "frameNo": 1, "amount": parseInt(self.itemAnnual().amount1()) });
            }

            if(self.itemAnnual().amount2()){
                years.push({ "frameNo": 2, "amount": parseInt(self.itemAnnual().amount2()) });
            }

            if(self.itemAnnual().amount3()){
                years.push({ "frameNo": 3, "amount": parseInt(self.itemAnnual().amount3()) });
            }

            if(self.itemAnnual().amount4()){
                years.push({ "frameNo": 4, "amount": parseInt(self.itemAnnual().amount4()) });
            }

            if(self.itemAnnual().amount5()){
                years.push({ "frameNo": 5, "amount": parseInt(self.itemAnnual().amount5()) });
            }

            command.months = months;
            command.years = years;
            command.handlings = handlings;
            self.$blockui("invisible");
            self.$ajax(Paths.REGISTER_SETTING_CMP, command).done(() => {
                self.$dialog.info({ messageId: 'Msg_15' }).then(() => {
                    self.loadData();
                });
            }).fail((res) => {
                self.$dialog.alert({ messageId: res.messageId });
            }).always(() => {
                self.$blockui("hide");
            });
        }

        loadDataScreenK(): void {
            const self = this;
            let dataHandling: Array<string> = [], alreadySettingList: Array<UnitAlreadySettingModel> = [], backgroundColor: string;
            
            self.$blockui("invisible");
            self.$ajax(Paths.GET_DEFAULT_INFO).done((data: any) => {

                if (data) {
                    self.listEmp(data.employments);
                    if(!self.isReloadScreenK()){
                        self.selectedCode(data.employments[0].code);
                    }
                   
                    self.name(data.employments[0].name)
                    _.each(data.employments, emp => {
                        _.each(data.employmentCodes, code =>{
                            if(emp.code === code){
                                alreadySettingList.push({code: emp.code, isAlreadySetting: true});
                            }                            
                        })                        
                    });

                    for (let i = 1; i < 6; i++) {
                        if (i <= data.listHandlingByNo.length && data.listHandlingByNo[i - 1].frameNo == i) {
                            backgroundColor = '#' + data.listHandlingByNo[i - 1].backgroundColor;
                        } else {
                            backgroundColor = '#FFFFFF';
                        }
                        dataHandling.push(backgroundColor);
                    }
                    $('#empt-list-setting').ntsListComponent(self.listComponentOption);
                    self.alreadySettingList(alreadySettingList);
                    self.itemHandlingScreenK(new ItemHandling(dataHandling));
                }

            }).always(() => {
                self.$blockui("hide");
            });
        }

        findDetail(code: string): void {
            const self = this;
            let dataMonthly: Array<string> = [], dataAnnual: Array<string> = [];
            let amountMonthly: string, amountAnnual: string;
            self.name(_.find(self.listEmp(), emp =>{return emp.code == code;}).name);
            self.$blockui("invisible");
            self.$ajax(Paths.GET_ESTIMATE_INFO_BY_CID_AND_CODE + "/" + code).done((data: any) => {
                if (data) {
                    for (let i = 1; i < 6; i++) {
                        if(i <= data.months.length && data.months[i-1].frameNo == i && data.months[i-1].amount > 0){
                            amountMonthly = data.months[i-1].amount;
                        } else {
                            amountMonthly = '';
                        }
                        if(i <= data.years.length && data.years[i-1].frameNo == i && data.years[i-1].amount > 0){
                             amountAnnual = data.years[i-1].amount;
                        } else {
                            amountAnnual ='';
                        }
                        dataMonthly.push(amountMonthly);
                        dataAnnual.push(amountAnnual);
                    }
                   
                } else {
                    for (let i = 1; i < 6; i++) {
                        amountMonthly = '';
                        amountAnnual ='';
                        dataMonthly.push(amountMonthly);
                        dataAnnual.push(amountAnnual);
                    }
                }
                self.itemMonthlyScreenK(new ItemMonthly(dataMonthly));
                self.itemAnnualScreenK(new ItemAnnual(dataAnnual));

            }).always(() => {
                self.$blockui("hide");
            });
        }

        registerMoneyEmp(): void {
            const self = this;            
            let command:any = {}, months: Array<ItemAmountModel> = [], 
            years: Array<ItemAmountModel> = [];

            if(self.itemMonthlyScreenK().amount1()){
                months.push({ "frameNo": 1, "amount": parseInt(self.itemMonthlyScreenK().amount1()) });
            }
            if(self.itemMonthlyScreenK().amount2()){
                months.push({ "frameNo": 2, "amount": parseInt(self.itemMonthlyScreenK().amount2()) });
            }
            if(self.itemMonthlyScreenK().amount3()){
                months.push({ "frameNo": 3, "amount": parseInt(self.itemMonthlyScreenK().amount3()) });
            }
            if(self.itemMonthlyScreenK().amount4()){
                months.push({ "frameNo": 4, "amount": parseInt(self.itemMonthlyScreenK().amount4()) });
            }
            if(self.itemMonthlyScreenK().amount5()){
                months.push({ "frameNo": 5, "amount": parseInt(self.itemMonthlyScreenK().amount5()) });
            }

            if(self.itemAnnualScreenK().amount1()){
                years.push({ "frameNo": 1, "amount": parseInt(self.itemAnnualScreenK().amount1()) });
            }

            if(self.itemAnnualScreenK().amount2()){
                years.push({ "frameNo": 2, "amount": parseInt(self.itemAnnualScreenK().amount2()) });
            }

            if(self.itemAnnualScreenK().amount3()){
                years.push({ "frameNo": 3, "amount": parseInt(self.itemAnnualScreenK().amount3()) });
            }

            if(self.itemAnnualScreenK().amount4()){
                years.push({ "frameNo": 4, "amount": parseInt(self.itemAnnualScreenK().amount4()) });
            }

            if(self.itemAnnualScreenK().amount5()){
                years.push({ "frameNo": 5, "amount": parseInt(self.itemAnnualScreenK().amount5()) });
            }

            command.employmentCode = self.selectedCode();
            command.months = months;
            command.years = years;
            self.$blockui("invisible");
            self.$ajax(Paths.REGISTER_SETTING_EMP, command).done(() => {
                self.$dialog.info({messageId: 'Msg_15'}).then(() => {
                    self.isReloadScreenK(true);
                    self.loadDataScreenK();
                    self.findDetail(self.selectedCode());     
                });
            }).fail((res) => {
                self.$dialog.alert({messageId: res.messageId});
            }).always(() => {
                self.$blockui("hide");
            });
        }

        public remove(): void {
            const self = this;           
            self.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes') =>{
                self.$blockui("invisible");
                let command: any = {
                    employmentCode: self.selectedCode()
                }
                
                if(result === 'yes'){
                    self.$ajax(Paths.DELETE_SETTING_EMP, command).done(() =>{
                        self.$dialog.info({messageId: "Msg_16"}).then(() =>{
                            if(self.alreadySettingList().length == 1) {
                                self.alreadySettingList([]);                                
                                self.selectedCode(self.listEmp()[0].code);
                            } else {
                                let indexSelected: number;
                                for(let index = 0; index < self.alreadySettingList().length; index++){
                                    if(self.alreadySettingList()[index].code == self.selectedCode()) {
                                        indexSelected = (index == self.alreadySettingList().length - 1) ? index -1: index;
                                        self.alreadySettingList.splice(index, 1);
                                        break;
                                    }
                                }
                                self.selectedCode(self.alreadySettingList()[indexSelected].code);
                            }
                        });
                    }).always(() =>{
                        self.$blockui("hide");
                    });    
                    self.$blockui("hide");                
                }
                if(result === 'no'){
                    self.$blockui("hide");
                    $('#outputSettingName').focus();
                }
            });            
        }

        openDialogScreenL(): void {
            const self = this;
            self.currentScreen = nts.uk.ui.windows.sub.modal('/view/kml/002/l/index.xhtml').onClosed(() => {
                self.loadData();
            });          
        }

        closeDialog(): void {
            const self = this;           
            self.$window.close();
        }      
        
        private validateAll(): boolean {
            const self = this;
            $('#colorpicker1').ntsEditor('validate');
            $('#colorpicker2').ntsEditor('validate');
            $('#colorpicker3').ntsEditor('validate');
            $('#colorpicker4').ntsEditor('validate');
            $('#colorpicker5').ntsEditor('validate');
            if (nts.uk.ui.errors.hasError()) {                    
                return true;
            }
            return false;
        }

        private clearError(): void {
            $('#colorpicker1').ntsError('clear');
            $('#colorpicker2').ntsError('clear');
            $('#colorpicker3').ntsError('clear');
            $('#colorpicker4').ntsError('clear');
            $('#colorpicker5').ntsError('clear');
        }
    }

    interface IEsimatedInfo {
        months: Array<ItemModel>;
        annuals: Array<ItemModel>;
        listHandlingByNo: Array<ItemModel>;
    }

    interface ItemModel {
        frameNo: number;
        data: string;
    }

    class EsimatedInfo {
        months: KnockoutObservableArray<ItemModel> = ko.observableArray([]);   
        annuals: KnockoutObservableArray<ItemModel> = ko.observableArray([]); 
        listHandlingByNo: KnockoutObservableArray<ItemModel> = ko.observableArray([]);    

        constructor(params ?: IEsimatedInfo) {
            const self = this;
            if(params){
                self.annuals(params.annuals);
                self.months(params.months);
                self.listHandlingByNo(params.listHandlingByNo);
            }
        }
    }

    class ItemHandling {
        backgroundColor1: KnockoutObservable<string> = ko.observable('');
        backgroundColor2: KnockoutObservable<string> = ko.observable('');
        backgroundColor3: KnockoutObservable<string> = ko.observable('');
        backgroundColor4: KnockoutObservable<string> = ko.observable('');
        backgroundColor5: KnockoutObservable<string> = ko.observable('');

        constructor(backgroundColors?: Array<string>) {
            const self = this;
            if(backgroundColors){
                self.backgroundColor1(backgroundColors[0]);
                self.backgroundColor2(backgroundColors[1]);
                self.backgroundColor3(backgroundColors[2]);
                self.backgroundColor4(backgroundColors[3]);
                self.backgroundColor5(backgroundColors[4]);
            } else {
                self.backgroundColor1('');
                self.backgroundColor2('');
                self.backgroundColor3('');
                self.backgroundColor4('');
                self.backgroundColor5('');
            }           
        }        
    }

    class ItemMonthly {
        amount1: KnockoutObservable<string> = ko.observable('');
        amount2: KnockoutObservable<string> = ko.observable('');
        amount3: KnockoutObservable<string> = ko.observable('');
        amount4: KnockoutObservable<string> = ko.observable('');
        amount5: KnockoutObservable<string> = ko.observable('');

        constructor(amounts?: Array<string>) {
            const self = this;
            if(amounts){
                self.amount1(amounts[0]);
                self.amount2(amounts[1]);
                self.amount3(amounts[2]);
                self.amount4(amounts[3]);
                self.amount5(amounts[4]);
            } else {
                self.amount1('');
                self.amount2('');
                self.amount3('');
                self.amount4('');
                self.amount5('');
            }           
        }
    }

    class ItemAnnual {
        amount1: KnockoutObservable<string> = ko.observable();
        amount2: KnockoutObservable<string> = ko.observable();
        amount3: KnockoutObservable<string> = ko.observable();
        amount4: KnockoutObservable<string> = ko.observable();
        amount5: KnockoutObservable<string> = ko.observable();

        constructor(amounts?: Array<string>) {
            const self = this;
            if(amounts){
                self.amount1(amounts[0]);
                self.amount2(amounts[1]);
                self.amount3(amounts[2]);
                self.amount4(amounts[3]);
                self.amount5(amounts[4]);
            } else {
                self.amount1('');
                self.amount2('');
                self.amount3('');
                self.amount4('');
                self.amount5('');
            }           
        }
    }

    class ItemHandlingModel {
        frameNo: number;
        backgroundColor: string;        
    }

    class ItemAmountModel {
        frameNo: number;
        amount: number;
    }

    class EmpModel {
        code: string;
        name: string;
    }
    
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
    
    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

}
