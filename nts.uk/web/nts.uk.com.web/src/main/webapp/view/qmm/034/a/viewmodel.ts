module qmm034.a.viewmodel {
    export class ScreenModel {
        constraint: string = 'LayoutCode';
        //list era;
        items: KnockoutObservableArray<EraModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        //layouts: KnockoutObservableArray<SingleSelectedCode>;
        currentCodeList: KnockoutObservableArray<any>;
        currentEra: KnockoutObservable<EraModel>;
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        //date picker
        date: KnockoutObservable<Date>;
        dateTime: KnockoutObservable<string>;
        eras: KnockoutObservableArray<qmm034.a.service.model.EraDto>;
        // selected row
        countItems: KnockoutObservable<number>;
        findIndex: KnockoutObservable<number>;
        isSelectdFirstRow: KnockoutObservable<boolean>;
        isDeleteEnable: KnockoutObservable<boolean>;
        inputCode: KnockoutObservable<string>;
        inputName: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.init();
            self.currentCode.subscribe(function(codeChanged) {
                if (codeChanged !== null) {
                    self.currentEra(self.getEra(codeChanged));
                    self.date(new Date(self.currentEra().startDate.toString()));
                    self.inputCode(self.currentEra().code);
                    self.inputName(self.currentEra().name);
                    self.currentCode(self.currentEra().code);
                }
            });
            self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.date()).toString());
        }

        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '元号', prop: 'code', width: 50 },
                { headerText: '記号', prop: 'name', width: 50 },
                { headerText: '開始年月日', prop: 'startDate', width: 80 },
            ]);
            self.currentEra = ko.observable((new EraModel('大明', 'S', new Date("1926/12/25"))));
            self.currentCode = ko.observable(null);
            self.date = ko.observable(new Date());
            self.dateTime = ko.observable('');
            self.eras = ko.observableArray([]);
            self.inputCode = ko.observable('');
            self.inputName = ko.observable('');
            self.findIndex = ko.observable(0);
            self.countItems = ko.observable(0);
            self.isSelectdFirstRow = ko.observable(true);
            self.isDeleteEnable = ko.observable(true);

        }
        refreshLayout(): void {
            let self = this;
            self.currentEra(new EraModel('', '', new Date()));
            self.currentCode(null);
            self.isUpdate(false);
            self.inputCode(null);
            self.inputName(null);
            self.date(new Date());
        }
        insertData(): any {
            let self = this;
            //let newData = self.currentEra();
            //let newEradata = self;
            // var x = self.items();
            //x.push(newData);
            //            if (self.isUpdate() === false) {
            //                self.items.push(newData);
            //                self.isUpdate = ko.observable(true);
            //            }
            let eraName: string;
            eraName = $('#A_INP_001').val();
            let eraMark: string;
            eraMark = $('#A_INP_002').val();
            let startDate = self.date();
            let endDate: string;
            let fixAttribute: number;
            let dfd = $.Deferred<any>();
            let node: qmm034.a.service.model.EraDto;
            node = new qmm034.a.service.model.EraDto(
                eraName, eraMark, startDate, endDate, fixAttribute
            );
            qmm034.a.service.addData(self.isUpdate(false), node).done(function(result) {
                self.reload().done(function() {
                    self.currentCode(eraName);
                    dfd.resolve();
                });
            }).fail(function(res) {

            });
            return dfd.promise();

        }
        alertDelete() {
            let self = this;
            if (confirm("do you wanna delete") === true) {
                self.deleteData();
            } else {
                alert("you didnt delete!");
            }
        }
        selectedItem(item): EraModel {
            var self = this;
            self.currentCode(item.code);
            return new EraModel(item.code, item.name, item.startDate);
        }
        reload() {
            var dfd = $.Deferred();
            var self = this;
            $.when(qmm034.a.service.getAllEras()).done(function(data) {
                self.buildGridDataSource(data);
                self.currentEra = ko.observable(_.cloneDeep(_.first(self.items())));
                dfd.resolve();

            }).fail(function(res) {

            });
            return dfd.promise();
        }
        deleteData() {
            let self = this;
            //            let newDel = self.currentEra();
            //            self.items.splice(self.items().indexOf(newDel), 1);
            let eraName: string;
            eraName = $('#A_INP_001').val();
            let eraMark: string;
            eraMark = $('#A_INP_002').val();
            let startDate = self.date();
            let dfd = $.Deferred<any>();
            let node: qmm034.a.service.model.EraDtoDelete;
            node = new qmm034.a.service.model.EraDtoDelete(startDate);

            qmm034.a.service.deleteData(node).done(function(result) {
                let rowIndex = _.findIndex(self.items(), function(item) {
                    return item.code == self.currentEra().code;
                })
                self.items.remove(function(item) {
                    return item.code == self.currentEra().code;
                });
                self.items.valueHasMutated();
                if (self.items().length === 0) {
                    self.isUpdate(false);
                    self.refreshLayout();
                } else if (self.items().length === rowIndex) {
                    self.currentEra(self.items()[rowIndex - 1]);
                    self.currentCode(self.items()[rowIndex - 1].code);
                } else {
                    self.currentEra(self.items()[rowIndex]);
                    self.currentCode(self.items()[rowIndex].code);
                }
                
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        getEra(codeNew): EraModel {
            let self = this;
            let era: EraModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });

            return _.cloneDeep(era);
        }
        update() {
            let self = this;
            //            if (self.currentCode() !== undefined && self.currentCode() !== null) {
            //                var newCurrentEra = _.findIndex(self.items(), function(item) {
            //                    return item.code === self.currentCode();
            //                });
            //                self.items.splice(newCurrentEra, 1, _.cloneDeep(self.currentEra()));
            //                self.items.valueHasMutated();
            //            }
            //            qmm034.a.service.updateData().done(function() {
            //                        self.start();
            //                        //console.log(self.items());
            //                    });
        }


        selectSomeItems() {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        }

        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }
        start(): JQueryPromise<any> {
            var self = this;

            // Page load dfd.
            var dfd = $.Deferred();

            // Resolve start page dfd after load all data.
            $.when(qmm034.a.service.getAllEras()).done(function(data) {
                dfd.resolve();

            }).fail(function(res) {

            });

            return dfd.promise();
        }
        startPage(): JQueryPromise<any> {
            var self = this;

            // Page load dfd.
            var dfd = $.Deferred();

            // Resolve start page dfd after load all data.
            $.when(qmm034.a.service.getAllEras()).done(function(data) {
                self.buildGridDataSource(data);
                self.currentEra = ko.observable((new EraModel('大明', 'S', new Date("1926/12/25"))));
                if (self.items().length > 0) {
                    self.currentEra = ko.observable(_.cloneDeep(_.first(self.items())));
                    self.currentCode(self.currentEra().code);
                }

                dfd.resolve();
            }).fail(function(res) {

            });

            return dfd.promise();
        }
        buildGridDataSource(items: any): any {
            let self = this;
            self.items([]);
            _.forEach(items, function(obj) {
                self.items.push(new EraModel(obj.eraName, obj.eraMark, obj.startDate));
            });
        }


    }
    //    class Era{
    //        eraName: KnockoutObservable<string>;
    //        eraMark: KnockoutObservable<string>;
    //        startDateEra: KnockoutObservable<Date>;    
    //        
    //        constructor(eraName: string, eraMark: string, startDateEra: Date){
    //                this.eraName = ko.observable(eraName);
    //                this.eraMark = ko.observable(eraMark);
    //                this.startDateEra = ko.observable(startDateEra);
    //        }
    //    }


   export class EraModel {
        code: string;
        name: string;
        startDate: Date
        // startDateText: string;


        constructor(code: string, name: string, startDate: Date) {
            this.code = code;
            this.name = name;
            this.startDate = startDate;
            //this.startDateText = startDate;
            //console.log(startDate.year);
            //this.startDateText = startDate.toDateString();
        }
    }
    //    class Era{
    //        eraCodeName:KnockoutObservable<string>;
    //        eraNameMark: KnockoutObservable<string>;
    //        eraStartDate: KnockoutObservable<string>;
    //        constructor(eraCodeName: string, eraNameMark: string, eraStartDate: string){
    //            this.eraCodeName = ko.observable(eraCodeName);
    //            this.eraNameMark = ko.observable(eraNameMark);
    //            this.eraStartDate = ko.observable(eraStartDate);    
    //        }    
    //    }
    //    class SingleSelectedCode {
    //        layout: KnockoutObservable<any>;
    //        strName: string;
    //        strShortcutName: string;
    //        strDate: string;
    //        constructor(shortCutName: string, Date: any) {
    //            let self = this;
    //            self.strName = name;
    //            self.strShortcutName = shortCutName;
    //            self.strDate = Date;
    //        }
    //    }


}