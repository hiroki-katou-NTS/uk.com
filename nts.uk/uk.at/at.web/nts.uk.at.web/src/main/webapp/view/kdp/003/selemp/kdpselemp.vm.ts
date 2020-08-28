module nts.uk.at.view.kdpselemp {

    ko.components.register('select-employee-by-name', {
        viewModel: {
            createViewModel: function(param, componentInfo) {
                var cvm = new ScreenModel(param.input, param.callback);
                return cvm;
            }
        },
        template: '<div id="PA">'
        + '  <!-- Datepicker -->'
        + '  <div id="PA1" data-bind="ntsDatePicker: { value: date }"></div>'
        + '  <div id="PA2_row1">'
        + '      <button id="a"  class="large fonttextbtn a"   data-bind="click: btnA">ア</button>'
        + '      <button id="ka" class="large fonttextbtn ka" data-bind="click: btnKA">カ</button>'
        + '      <button id="sa" class="large fonttextbtn sa" data-bind="click: btnSA">サ</button>'
        + '      <button id="ta" class="large fonttextbtn ta" data-bind="click: btnTA">タ</button>'
        + '      <button id="na" class="large fonttextbtn na" data-bind="click: btnNA">ナ</button>'
        + '      <button id="ha" class="large fonttextbtn ha" data-bind="click: btnHA">ハ</button>'
        + '  </div>'
        + '  <div id="PA2_row2">'
        + '      <button id="ma" class="large fonttextbtn ma"   data-bind="click: btnMA">マ</button>'
        + '      <button id="ya" class="large fonttextbtn ya"   data-bind="click: btnYA">ヤ</button>'
        + '      <button id="ra" class="large fonttextbtn ra"   data-bind="click: btnRA">ラ</button>'
        + '      <button id="wa" class="large fonttextbtn wa"   data-bind="click: btnWA">ワ</button>'
        + '      <button id="all" class="large fonttextbtn all" data-bind="click: btnALL">全員</button>'
        + '  </div>'
        + '  <div>'
        + '      <span id="PA4" class="label" title="">一覧にない社員で打刻する</span>'
        + '  </div>'
        + '  <div class="row">'
        + '       <div id="gridListEmployeesContent">'
        + '           <table id="gridListEmployees" tabindex="7"></table>'
        + '       </div>'
        + '  </div>'
        + '</div>'
    });

    class ScreenModel {
        date: KnockoutObservable<string>;
        listEmp: KnockoutObservableArray<Employee>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        currentEmp: KnockoutObservable<any>;

        constructor(param, callback) {
            var self = this;

            let today = moment(new Date(), 'YYYY/MM/DD');
            let year = today.format('YYYY');
            let month = today.format('MM');
            let day = today.format('DD');
            self.date = ko.observable(year + '' + month + '' + day);

            self.listEmp = ko.observableArray([]);
            self.columns2 = ko.observableArray([
                { headerText: "id", key: 'id', width: 0, hidden: true },
                { headerText: "<div style='text-align: center;'></div>", key: 'name', width: 222 }
            ]);

            
            self.listEmp.push(new Employee('00' + 1, '01', '', 'ア大塚', "<div style='text-align: center;'>" + '000001 ア大塚' + "</div>", 'ア'));
            self.listEmp.push(new Employee('00' + 2, '02','', 'イ大塚',  "<div style='text-align: center;'>" + '000002 イ大塚' + "</div>", 'イ'));
            self.listEmp.push(new Employee('00' + 3, '03', '','ウ大塚',   "<div style='text-align: center;'>" + '000003 ウ大塚' + "</div>", 'ウ'));
            self.listEmp.push(new Employee('00' + 4, '04', '','エ大塚',   "<div style='text-align: center;'>" + '000004 エ大塚' + "</div>", 'エ'));
            self.listEmp.push(new Employee('00' + 5, '05', '','オ大塚',   "<div style='text-align: center;'>" + '000005 オ大塚' + "</div>", 'オ'));
            self.listEmp.push(new Employee('00' + 6, '06','', 'ァ大塚',   "<div style='text-align: center;'>" + '000006 ァ大塚' + "</div>", 'ァ'));
            self.listEmp.push(new Employee('0' + 66, '05','', 'ァ大塚',   "<div style='text-align: center;'>" + '000005 ァ大塚' + "</div>", 'ァ'));
            self.listEmp.push(new Employee('00' + 7, '07', '','ィ大塚',   "<div style='text-align: center;'>" + '000007 ィ大塚' + "</div>", 'ィ'));
            self.listEmp.push(new Employee('00' + 8, '08','', 'ゥ大塚',   "<div style='text-align: center;'>" + '000008 ゥ大塚' + "</div>", 'ゥ'));
            self.listEmp.push(new Employee('00' + 9, '09', '','ェ大塚',   "<div style='text-align: center;'>" + '000009 ェ大塚' + "</div>", 'ェ'));
            self.listEmp.push(new Employee('00' + 10, '10','', 'ォ大塚',   "<div style='text-align: center;'>" + '000010 ォ大塚' + "</div>", 'ォ'));
            self.listEmp.push(new Employee('00' + 11, '11', '','ヴ大塚',  "<div style='text-align: center;'>" + '000011 ヴ大塚' + "</div>", 'ヴ'));
            
            
            self.listEmp.push(new Employee('00' + 12, '12','', 'カ大塚',   "<div style='text-align: center;'>" + '000012 カ大塚' + "</div>", 'カ'));
            self.listEmp.push(new Employee('00' + 13, '13','', 'キ大塚',   "<div style='text-align: center;'>" + '000013 キ大塚' + "</div>", 'キ'));
            self.listEmp.push(new Employee('00' + 14, '14','', 'ク大塚',   "<div style='text-align: center;'>" + '000014 ク大塚' + "</div>", 'ク'));
            self.listEmp.push(new Employee('00' + 15, '15','', 'ケ大塚',   "<div style='text-align: center;'>" + '000015 ケ大塚' + "</div>", 'ケ'));
            self.listEmp.push(new Employee('00' + 16, '16','', 'コ大塚',   "<div style='text-align: center;'>" + '000016 コ大塚' + "</div>", 'コ'));
            self.listEmp.push(new Employee('00' + 17, '17','', 'ガ大塚',   "<div style='text-align: center;'>" + '000017 ガ大塚' + "</div>", 'ガ'));
            self.listEmp.push(new Employee('00' + 18, '18','', 'ギ大塚',   "<div style='text-align: center;'>" + '000018 ギ大塚' + "</div>", 'ギ'));
            self.listEmp.push(new Employee('00' + 19, '19','', 'グ大塚',   "<div style='text-align: center;'>" + '000019 グ大塚' + "</div>", 'グ'));
            self.listEmp.push(new Employee('00' + 20, '20','', 'ゲ大塚',   "<div style='text-align: center;'>" + '000020 ゲ大塚' + "</div>", 'ゲ'));
            self.listEmp.push(new Employee('00' + 21, '21','', 'ゴ大塚',   "<div style='text-align: center;'>" + '000021 ゴ大塚' + "</div>", 'ゴ'));
            self.listEmp.push(new Employee('00' + 22, '22','', 'ヵ大塚',   "<div style='text-align: center;'>" + '000022 ヵ大塚' + "</div>", 'ヵ'));
            self.listEmp.push(new Employee('00' + 23, '23','', 'ヶ大塚',   "<div style='text-align: center;'>" + '000023 ヶ大塚' + "</div>", 'ヶ'));
            
            self.listEmp.push(new Employee('00' + 24, '24','', 'サ大塚',   "<div style='text-align: center;'>" + '000024 サ大塚' + "</div>", 'サ'));
            self.listEmp.push(new Employee('00' + 25, '25','','シ大塚',   "<div style='text-align: center;'>" + '000025 シ大塚' + "</div>", 'シ'));
            self.listEmp.push(new Employee('00' + 26, '26','', 'ス大塚',   "<div style='text-align: center;'>" + '000026 ス大塚' + "</div>", 'ス'));
            self.listEmp.push(new Employee('00' + 27, '27','', 'セ大塚',   "<div style='text-align: center;'>" + '000027 セ大塚' + "</div>", 'セ'));
            self.listEmp.push(new Employee('00' + 28, '28','', 'ソ大塚',   "<div style='text-align: center;'>" + '000028 ソ大塚' + "</div>", 'ソ'));
            self.listEmp.push(new Employee('00' + 29, '29','', 'ザ大塚',   "<div style='text-align: center;'>" + '000029 ザ大' + "</div>", 'ザ'));
            self.listEmp.push(new Employee('00' + 30, '30','', 'ジ大塚',   "<div style='text-align: center;'>" + '000030 ジ大塚' + "</div>", 'ジ'));
            self.listEmp.push(new Employee('00' + 31, '31','', 'ズ大塚',   "<div style='text-align: center;'>" + '000031 ズ大塚' + "</div>", 'ズ'));
            self.listEmp.push(new Employee('00' + 32, '32','', 'ゼ大塚',   "<div style='text-align: center;'>" + '000032 ゼ大塚' + "</div>", 'ゼ'));
            self.listEmp.push(new Employee('00' + 33, '33','', 'ゾ大塚',   "<div style='text-align: center;'>" + '000033 ゾ大塚' + "</div>", 'ゾ'));

            self.currentEmp = ko.observable();
            self.date.subscribe(newValue => {
                console.log(newValue);
            });


            $("#PA4").click(function() {
                $('#gridListEmployees').igGridSelection('clearSelection');
                self.eventSelectPA4();
                if (callback) {
                    callback(undefined);
                }
            });

            //row click
            $(document).delegate("#gridListEmployees", "iggridcellclick", function(evt, ui) {
                var value = _.find(self.listEmp(), ['id', ui.rowKey]);

                var rowIndexSelected = $('#gridListEmployees').igGridSelection("selectedRow");
                if (typeof (rowIndexSelected) != 'undefined' && rowIndexSelected != null) {
                    self.eventUnSelectPA4();
                    if (callback) {
                        callback(value);
                    }
                } else {
                    if (callback) {
                        callback(undefined);
                    }
                }
            });

            self.bindData(self.listEmp());
        }
        
        eventSelectPA4() {
            let self = this;
            $("#PA4").css("background-color", "#007fff");
            $("#PA4").css("color", "#ffffff");
        }
        
        eventUnSelectPA4() {
            let self = this;
            $("#PA4").css("background-color", "#ffffff");
            $("#PA4").css("color", "black");    
        }

        public startPage(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            dfd.resolve();
            return dfd.promise();
        }

        private btnA() {
            let self = this;
            $("#a").css("background-color", "#30b0d5");
            let listId = ["#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            self.eventUnSelectPA4();
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.filterData('ア');
        }

        private btnKA() {
            let self = this;
            $("#ka").css("background-color", "#30b0d5");
            let listId = ["#a", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            self.eventUnSelectPA4();
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.filterData('カ');
        }

        private btnSA() {
            let self = this;
            $("#sa").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('サ');
        }

        private btnTA() {
            let self = this;
            $("#ta").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('タ');

        }

        private btnNA() {
            let self = this;
            $("#na").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('ナ');
        }

        private btnHA() {
            let self = this;
            $("#ha").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('ハ');
        }

        private btnMA() {
            let self = this;
            $("#ma").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('マ');
        }

        private btnYA() {
            let self = this;
            $("#ya").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('ヤ');
        }

        private btnRA() {
            let self = this;
            $("#ra").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('ラ');
        }

        private btnWA() {
            let self = this;
            $("#wa").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#all"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();
            self.filterData('ワ');
        }

        private btnALL() {
            let self = this;
            $("#all").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa"];
            self.setBackGroundBtnOthers(listId);
            $('#gridListEmployees').igGridSelection('clearSelection');
            self.eventUnSelectPA4();

            $("#gridListEmployees").igGrid("option", "dataSource", self.listEmp());
        }

        private filterData(data) {
            let self = this;
            let dataSource = self.listEmp();
            let dataSourceAfterFilter = [];

            switch (data) {
                case 'ア':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'ア') || _.eq(startsWith, 'イ') || _.startsWith(startsWith, 'ウ') 
                            || _.eq(startsWith, 'エ') || _.eq(startsWith, 'オ')
                            || _.eq(startsWith, 'ァ') || _.eq(startsWith, 'ィ') 
                            || _.eq(startsWith, 'ゥ') || _.eq(startsWith, 'ェ') 
                            || _.eq(startsWith, 'ォ') || _.eq(startsWith, 'ヴ'); 
                        });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'カ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return  _.eq(startsWith, 'カ') || _.eq(startsWith, 'キ') || _.eq(startsWith, 'ク') 
                             || _.eq(startsWith, 'ケ') || _.eq(startsWith, 'コ')
                             || _.eq(startsWith, 'ガ') || _.eq(startsWith, 'ギ') 
                             || _.eq(startsWith, 'グ') || _.eq(startsWith, 'ゲ') 
                             || _.eq(startsWith, 'ゴ') 
                             || _.eq(startsWith, 'ヵ') || _.eq(startsWith, 'ヶ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'サ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'サ') || _.eq(startsWith, 'シ') 
                            || _.eq(startsWith, 'ス') || _.eq(startsWith, 'セ') 
                            || _.eq(startsWith, 'ソ') || _.eq(startsWith, 'ザ') 
                            || _.eq(startsWith, 'ジ') || _.eq(startsWith, 'ズ') 
                            || _.eq(startsWith, 'ゼ') || _.eq(startsWith, 'ゾ');
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'タ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'タ') || _.eq(startsWith, 'チ') 
                            || _.eq(startsWith, 'ツ') || _.eq(startsWith, 'テ') 
                            || _.eq(startsWith, 'ト') || _.eq(startsWith, 'ダ')
                            || _.eq(startsWith, 'ヂ') || _.eq(startsWith, 'ヅ')
                            || _.eq(startsWith, 'デ') || _.eq(startsWith, 'ド')
                            || _.eq(startsWith, 'ッ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'ナ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'ナ') || _.eq(startsWith, 'ニ')  
                            || _.eq(startsWith, 'ヌ') || _.eq(startsWith, 'ネ') 
                            || _.eq(startsWith, 'ノ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'ハ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) { 
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'ハ') || _.eq(startsWith, 'ヒ') 
                            || _.eq(startsWith, 'フ') || _.eq(startsWith, 'ヘ') 
                            || _.eq(startsWith, 'ホ') || _.eq(startsWith, 'バ')
                            || _.eq(startsWith, 'ビ') || _.eq(startsWith, 'ブ')
                            || _.eq(startsWith, 'ベ') || _.eq(startsWith, 'ボ')
                            || _.eq(startsWith, 'パ') || _.eq(startsWith, 'ピ')
                            || _.eq(startsWith, 'プ') || _.eq(startsWith, 'ペ') 
                            || _.eq(startsWith, 'ポ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'マ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'マ') || _.eq(startsWith, 'ミ')
                            || _.eq(startsWith, 'ム') || _.eq(startsWith, 'メ')
                            || _.eq(startsWith, 'モ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'ヤ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith; 
                        return _.eq(startsWith, 'ヤ') || _.eq(startsWith, 'ユ') 
                            || _.eq(startsWith, 'ヨ') || _.eq(startsWith, 'ャ') 
                            || _.eq(startsWith, 'ュ') || _.eq(startsWith, 'ョ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'ラ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'ラ') || _.eq(startsWith, 'リ')  
                            || _.eq(startsWith, 'ル') || _.eq(startsWith, 'レ') 
                            || _.eq(startsWith, 'ロ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
                case 'ワ':
                    dataSourceAfterFilter = _.filter(dataSource, function(o) {
                        let startsWith = o.startsWith;
                        return _.eq(startsWith, 'ワ') || _.eq(startsWith, 'ヲ')  
                            || _.eq(startsWith, 'ン') || _.eq(startsWith, 'ヮ'); 
                    });
                    let order = _.orderBy(dataSourceAfterFilter, ['nameKana', 'code'], ['asc', 'asc']);
                    $("#gridListEmployees").igGrid("option", "dataSource", order);
                    break;
            }
        }
        
        

        private setBackGroundBtnOthers(listId) {
            let self = this;
            _.forEach(listId, (id) => {
                $(id).css("background-color", "#738088");
            });
        }

        private bindData(listEmp): void {
            var self = this;
            $("#gridListEmployees").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'id',
                columns: [
                    { headerText: "id", key: 'id', width: 0, hidden: true },
                    { headerText: "<div style='text-align: center;'></div>", key: 'textToDisplay', width: 222 }
                ],
                dataSource: listEmp,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: 553,
                width: 240,
                tabIndex: 7,
                features: [
                    {
                        name: "Selection",
                        mode: "row",
                        multipleSelection: false,
                    },
                    {
                        name: 'Filtering',
                        type: 'local',
                        mode: 'simple'
                    },
                    {
                        name: 'Sorting',
                        type: 'local'
                    },
                    {
                        name: 'Resizing'
                    },
                    {
                        name: "Tooltips"
                    }
                ]
            });
        }

    }

    export class Employee {
        id: string;
        code: string;
        name: string;
        nameKana: string;
        textToDisplay : string;
        startsWith : string;
        constructor(id: string, code: string, name: string, nameKana: string, textToDisplay: string, startsWith : string) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.nameKana= nameKana;
            this.textToDisplay = textToDisplay; // truờng nay dev them để code dề hơn = Code + BussinessName
            this.startsWith = startsWith;// truờng nay dev them để code dề hơn (ký tự đầu tiên của BussinessNameKana)

        }
    }
}