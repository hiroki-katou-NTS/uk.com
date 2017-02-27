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

        constructor() {
            let self = this;


            /*gridList*/
            self.items = ko.observableArray([
                //new EraModel('明明', 'M', "1999/01/25"),
                //new EraModel('大正', 'T', "1912/07/30"),
                //                new EraModel('大明', 'S', "1926/12/25"),
                //                new EraModel('大元', 'H', "1989/01/08"),
                //                new EraModel('大記', 'N', "2016/02/18"),
            ]);
            self.columns = ko.observableArray([
                { headerText: '元号', prop: 'code', width: 50 },
                { headerText: '記号', prop: 'name', width: 50 },
                { headerText: '開始年月日', prop: 'startDateText', width: 80 },
            ]);
            self.currentCodeList = ko.observableArray([]);
            //Tim object dau tien
            self.currentEra = ko.observable(null);
            self.currentCode = ko.observable();
            self.currentCode.subscribe(function(codeChanged) {
                self.currentEra(self.getEra(codeChanged));
                self.date(self.currentEra().startDate);
            });
            /*datePicker*/
            // var datePicker = self.currentEra();
            self.date = ko.observable(new Date());
            self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.date()).toString());

            self.eras = ko.observableArray([]);
            console.log(self.items());
            /*selected row*/
            self.findIndex = ko.observable(0);
            self.countItems = ko.observable(0);
            self.isSelectdFirstRow = ko.observable(true);
            self.isDeleteEnable = ko.observable(true);

        }

        //        register() {
        //            let self = this;
        //            if (self.isUpdate() === false) {
        //                self.insertData();
        //            } else {
        //                self.update();
        //            }
        //        }
        refreshLayout(): void {
            let self = this;
            self.currentEra(new EraModel('', '', new Date()));
            self.isUpdate = ko.observable(false);
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
                    dfd.resolve();
                });
            }).fail(function(res) {

            });
            return dfd.promise();

        }
        alertDelete() {
            let self = this;
            let indexEra = _.findIndex(self.items(), function(item) {
                return item.code == self.currentEra().code;
            });
            let count = self.items().length;
            if (confirm("do you wanna delete") === true) {
                self.deleteData();
                if (indexEra == count - 1) {
                    self.currentCode(self.items()[self.items().length - 2 ].code);
                } else {
                    self.currentCode(self.items()[indexEra - 1 ].code);
                }
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
                self.countItems(data.length);
                if (data.length > 0) {
                    if (self.isSelectdFirstRow()) {
                        self.currentEra(self.selectedItem(data[0]));
                        self.isSelectdFirstRow(false);
                    }
                    //self.items(data);    
                }
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
            let endDate: string;
            let fixAttribute: number;
            let dfd = $.Deferred<any>();
            let node: qmm034.a.service.model.EraDto;
            node = new qmm034.a.service.model.EraDto(
                eraName, eraMark, startDate, endDate, fixAttribute
            );
            qmm034.a.service.deleteData(node).done(function(result) {
                self.reload().done(function() {
                    //                    var data = self.items();
                    //                    if(self.countItems == self.findIndex){
                    //                        self.currentEra(data(self.findIndex()-1));    
                    //                    }
                    //                    let indexDele = ._findIndex(self.items, function(index){
                    //                            return index.items == self.currentEra().code;
                    //                    });
                    dfd.resolve();
                });
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
                self.currentEra = ko.observable(_.cloneDeep(_.first(self.items())));
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


    class EraModel {
        code: string;
        name: string;
        startDate: Date;
        startDateText: string;


        constructor(code: string, name: string, startDate: string) {
            this.code = code;
            this.name = name;
            this.startDate = new Date(startDate);
            this.startDateText = startDate;
            //console.log(startDate.year);
            //this.startDateText = startDate.toDateString();
        }
    }
    class Era {
        eraCodeName: KnockoutObservable<string>;
        eraNameMark: KnockoutObservable<string>;
        eraStartDate: KnockoutObservable<string>;
        constructor(eraCodeName: string, eraNameMark: string, eraStartDate: string) {
            this.eraCodeName = ko.observable(eraCodeName);
            this.eraNameMark = ko.observable(eraNameMark);
            this.eraStartDate = ko.observable(eraStartDate);
        }
    }
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