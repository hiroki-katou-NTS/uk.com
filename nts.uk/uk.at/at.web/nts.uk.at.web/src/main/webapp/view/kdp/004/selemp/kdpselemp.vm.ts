module nts.uk.at.view.kdpselemp {

    ko.components.register('select-employee-by-name', {
        viewModel: {
            createViewModel: function(param, componentInfo) {
                var cvm = new ScreenModel(param.input, param.callback);
                return cvm;
            }
        },
        template:   '<div id="PA">'
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

            for (let i = 1; i < 100; i++) {
                self.listEmp.push(new Employee('00' + i, 'code', "<div style='text-align: center;'>" + '000002 大塚　次郎BB (男性)' + "</div>"));
            }
            self.currentEmp = ko.observable();
            self.date.subscribe(newValue => {
                console.log(newValue);
            });


            $("#PA4").click(function() {
                $('#gridListEmployees').igGridSelection('clearSelection');

                $("#PA4").css("background-color", "#007fff");
                $("#PA4").css("color", "#ffffff");
                if (callback) {
                    callback(undefined);
                }
            });

            //row click
            $(document).delegate("#gridListEmployees", "iggridcellclick", function(evt, ui) {
                var value = _.find(self.listEmp(), ['id', ui.rowKey]);

                var rowIndexSelected = $('#gridListEmployees').igGridSelection("selectedRow");
                if (typeof (rowIndexSelected) != 'undefined' && rowIndexSelected != null) {
                    $("#PA4").css("background-color", "#ffffff");
                    $("#PA4").css("color", "black");
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
            
            let ds = [];
            for (let i = 1; i < 5; i++) {
                ds.push(new Employee('00' + i, 'code', "<div style='text-align: center;'>" + '000002 大塚　次郎BB (男性)' + "</div>"));
            }
            
            //Set
            $("#gridListEmployees").igGrid("option", "dataSource", ds);
        }

        private btnKA() {
            let self = this;
            $("#ka").css("background-color", "#30b0d5");
            let listId = ["#a", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnSA() {
            let self = this;
            $("#sa").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnTA() {
            let self = this;
            $("#ta").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#na", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);

        }

        private btnNA() {
            let self = this;
            $("#na").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#ha", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnHA() {
            let self = this;
            $("#ha").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ma", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnMA() {
            let self = this;
            $("#ma").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ya", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnYA() {
            let self = this;
            $("#ya").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ra", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnRA() {
            let self = this;
            $("#ra").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#wa", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnWA() {
            let self = this;
            $("#wa").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#all"];
            self.setBackGroundBtnOthers(listId);
        }

        private btnALL() {
            let self = this;
            $("#all").css("background-color", "#30b0d5");
            let listId = ["#a", "#ka", "#sa", "#ta", "#na", "#ha", "#ma", "#ya", "#ra", "#wa"];
            self.setBackGroundBtnOthers(listId);
        }

        setBackGroundBtnOthers(listId) {
            let self = this;
            _.forEach(listId, (id) => {
                $(id).css("background-color", "#738088");
            });
        }
        
        public bindData(listEmp): void {
            var self = this;
            $("#gridListEmployees").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'id',
                columns: [
                    { headerText: "id", key: 'id', width: 0, hidden: true },
                    { headerText: "<div style='text-align: center;'></div>", key: 'name', width: 222 }
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
        constructor(id: string, code: string, name: string) {
            this.id = id;
            this.code = code;
            this.name = name;

        }
    }
}