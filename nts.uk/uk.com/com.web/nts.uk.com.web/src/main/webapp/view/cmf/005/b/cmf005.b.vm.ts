module nts.uk.com.view.cmf005.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf005.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {


        //Radio button
        rdSelected: KnockoutObservable<any>;
        optionCategory: KnockoutObservable<any>;
        optionDeleteSet: KnockoutObservable<any>;

        //information category
        deleteSetName: KnockoutObservable<string>;

        // dialog
        codeConvertCode: KnockoutObservable<model.AcceptanceCodeConvert>;

        //B5_3
        listDataCategory: KnockoutObservableArray<ItemModel>;
        listColumnHeader: KnockoutObservableArray<NtsGridListColumn>;
        selectedCsvItemNumber: KnockoutObservable<number> = ko.observable(null);
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        //datepicker
        enable: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        dateValue1: KnockoutObservable<any>;
        dateValue2: KnockoutObservable<any>;
        dateValue3: KnockoutObservable<any>;
        startDateDailyString: KnockoutObservable<string>;
        endDateDailyString: KnockoutObservable<string>;
        startDateMothlyString: KnockoutObservable<string>;
        endDateMothlyString: KnockoutObservable<string>;
        startDateYearlyString: KnockoutObservable<string>;
        endDateYearlyString: KnockoutObservable<string>;

        //B7_1
        saveBeforDeleteOption: KnockoutObservableArray<model.ItemModel>;
        isSaveBeforeDeleteFlg: number;

        //B8_1
        isExistCompressPasswordFlg: KnockoutObservable<boolean>;
        isDisplayCompressPassword: KnockoutObservable<boolean>;
        passwordForCompressFile: KnockoutObservable<string>;
        confirmPasswordForCompressFile: KnockoutObservable<string>;

        constructor() {
            var self = this;

            self.initComponents();
            self.setDefault();

        }
        
        setDefault() {
            var self = this;
            //set B3_1 "ON"
            self.rdSelected = ko.observable(1);

            //B6_2_2
            var dateCurrent = moment.utc(new Date(), "YYYY/MM/DD");
//            var dateCurrent = moment.utc("2019/3/28", "YYYY/MM/DD");
            var dateNow = dateCurrent.add(1,"M");
            console.log("Date now========Day:"+ dateNow.get('date') + "/Moth:" + dateNow.get('month') + "/Year :" + dateNow.get('year'));
            var caculaterMoth = dateNow.add(-1, "M");
            console.log("caculaterMoth========Day:"+ caculaterMoth.get('date') + "/Moth:" + caculaterMoth.get('month') + "/Year :" + caculaterMoth.get('year'));
            var caculaterDay = caculaterMoth.add(1, "d");
                     
            
            var getDay = caculaterMoth.get('date');
            var getMoth = caculaterMoth.get('month');
            var getYear = caculaterMoth.get('year');
            console.log("caculaterDay========Day:"+ getDay + "/Moth:" + getMoth + "/Year :" + getYear);
           

           
//            self.startDateDailyString = ko.observable(getYear+"/"+getMoth+"/"+getDay);
//            self.endDateDailyString = ko.observable(getYear+"/"+getMoth+"/"+getDay);
//
//            self.startDateMothlyString = ko.observable(getDayStart);
//            self.endDateMothlyString = ko.observable(dateNow);
//
//            self.startDateYearlyString = ko.observable(getDayStart);
//            self.endDateYearlyString = ko.observable(dateNow);
        }
        
        initComponents() {
            var self = this;

            //View menu step
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.activeStep = ko.observable(0);
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });

            //Radio button
            self.optionCategory = ko.observable({ value: 1, text: nts.uk.resource.getText("CMF005_15") });
            self.optionDeleteSet = ko.observable({ value: 2, text: nts.uk.resource.getText("CMF005_16") });

            //B5_3
            this.listDataCategory = ko.observableArray([]);
            this.listColumnHeader = ko.observableArray([
                { headerText: nts.uk.resource.getText("CMF005_24"), key: 'code', width: 150, hidden: true },
                { headerText: nts.uk.resource.getText("CMF005_25"), key: 'name', width: 20, hidden: true },
                { headerText: nts.uk.resource.getText("CMF005_26"), key: 'description', width: 30 }
            ]);

            //DatePcicker
            self.enable = ko.observable(true);
            self.required = ko.observable(true);

            self.startDateDailyString = ko.observable("");
            self.endDateDailyString = ko.observable("");

            self.startDateMothlyString = ko.observable("");
            self.endDateMothlyString = ko.observable("");

            self.startDateYearlyString = ko.observable("");
            self.endDateYearlyString = ko.observable("");

            self.dateValue1 = ko.observable({});
            self.dateValue2 = ko.observable({});
            self.dateValue3 = ko.observable({});

            self.startDateDailyString.subscribe(function(value) {
                self.dateValue1().startDate = value;
                self.dateValue1.valueHasMutated();
            });
            self.endDateDailyString.subscribe(function(value) {
                self.dateValue1().endDate = value;
                self.dateValue1.valueHasMutated();
            });

            self.startDateMothlyString.subscribe(function(value) {
                self.dateValue2().startDate = value;
                self.dateValue2.valueHasMutated();
            });
            self.endDateMothlyString.subscribe(function(value) {
                self.dateValue2().endDate = value;
                self.dateValue2.valueHasMutated();
            });

            self.startDateYearlyString.subscribe(function(value) {
                self.dateValue3().startDate = value;
                self.dateValue3.valueHasMutated();
            });
            self.endDateYearlyString.subscribe(function(value) {
                self.dateValue3().endDate = value;
                self.dateValue3.valueHasMutated();
            });


            //B7_1
            self.saveBeforDeleteOption = ko.observableArray([
                new model.ItemModel(model.SAVE_BEFOR_DELETE_ATR.YES, getText('CMF005_35')),
                new model.ItemModel(model.SAVE_BEFOR_DELETE_ATR.NO, getText('CMF005_36'))
            ]);
            self.isSaveBeforeDeleteFlg = ko.observable(model.SAVE_BEFOR_DELETE_ATR.YES);

            //B8_1
            self.isExistCompressPasswordFlg = ko.observable(true);
            self.isDisplayCompressPassword = ko.observable(true);
            self.passwordForCompressFile = ko.observable("");
            self.confirmPasswordForCompressFile = ko.observable("");

        }
        // get status display button select category
        isEnableBtnOpenC() {
            var self = this;
            if (self.rdSelected() == 1) {
                return true;
            } else {
                return false;
            }
        }

        // Open screen C
        openScreenC() {
            var self = this;
            //                let codeConvertCode = self.codeConvertCode();
            //                setShared("CMF005cParams", { selectedConvertCode: ko.toJS(codeConvertCode) });
            modal("/view/cmf/005/c/index.xhtml").onClosed(() => {
                //                    let params = getShared("CMF005cOutput");
                //                    if (!nts.uk.util.isNullOrUndefined(params)) {
                //                        let codeConvertCodeSelected = params.selectedConvertCodeShared;
                //                        self.codeConvertCode(codeConvertCodeSelected);
                //                        self.numDataFormatSetting().codeConvertCode(codeConvertCodeSelected.dispConvertCode);
                //                    }
                //                    $('#G4_2').focus();
            });
        }

        // Open screen D
        nextScreenD() {
            nts.uk.request.jump("/view/cmf/005/d/index.xhtml");
        }

        // Open screen A
        backScreenA() {
            nts.uk.request.jump("/view/cmf/005/a/index.xhtml");
        }



    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}


