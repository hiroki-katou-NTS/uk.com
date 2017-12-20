module nts.uk.at.view.kmk012.e {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            //List clousureEmpAddDto to insert del all.
            clousureEmpAddDto: ClousureEmpAddDto;

            //list items on grid.
            items: Array<any>;

            //Param from KMK012E screen.
            startDate: number;

            //Data respone from Ws.
            closureEmployDto: ClosureEmployDto;

            constructor() {
                let self = this;

                //List clousureEmpAddDto to insert del all.
                self.clousureEmpAddDto = null;
                
                //Init list data on grid.
                self.items = null;

                //Get startDate from KMK012E screen.
                self.startDate = getShared("startDate");

                //Init data combobox
                self.closureEmployDto = new ClosureEmployDto(null, null);
            }

            //Insert del in server.
            insertDelArray() {
                let self = this;
                var dfd = $.Deferred();
                
                var source = $("#grid2").igGrid("option", "dataSource");
                alert(source[0].name);
                
                //Only get item when closureId != -1
                _.forEach(self.items, function(item) {
                     if (item.closureId != -1) {
                         self.clousureEmpAddDto.empCdNameList.push(item);
                     }
                });
                
                //Insert del in server.
                service.insertDelArray(self.clousureEmpAddDto).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();

                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });

                return dfd.promise();
            }

            getClosureEmploy(startDate) {
                let self = this;
                var dfd = $.Deferred();

                //Get ClosureEmploy with param: startDate and return: data 
                service.getClosureEmploy(startDate).done(function(data) {
                    self.closureEmployDto = new ClosureEmployDto(data.empCdNameList, data.closureCdNameList);

                    //View data in ClousureEmploy
                    self.loadGrid(self.closureEmployDto);

                    dfd.resolve();
                });

                return dfd.promise();
            }

            loadGrid(closureEmployDto) {
                let self = this;
                //Init data for list item on grid.
                self.items = closureEmployDto.empCdNameList;
                //If closureID = null init value -1
                self.items = _.map(self.items, function(item) {
                    if (item.closureId == null) {
                        item.closureId = -1;
                        return item;
                    } else {
                        return item;
                    }
                });

                //Init data for combobox.
                var comboItems = closureEmployDto.closureCdNameList;
                //Insert comboColumns with value = 0;
                var closureCdNameDto = new ClosureCdNameDto(-1, '');
                comboItems.unshift(closureCdNameDto);
                var comboColumns = [{ prop: 'closureId', length: 4 },
                    { prop: 'closureName', length: 8 }];

                //View list data on grid.
                $("#grid2").ntsGrid({
                    width: '390px',
                    height: '380px',
                    dataSource: self.items,
                    primaryKey: 'code',
                    virtualization: true,
                    virtualizationMode: 'continuous',

                    //columns on grid list.
                    columns: [
                        { headerText: 'ID', key: 'code', dataType: 'number', width: '50px', hidden: true },
                        { headerText: getText('KMK012_38'), key: 'name', dataType: 'string', width: '150px' },
                        { headerText: getText('KMK012_39'), key: 'closureId', dataType: 'string', width: '180px', ntsControl: 'Combobox' }
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],

                    //Defind combobox and other control
                    ntsControls: [

                        { name: 'Combobox', options: comboItems, optionsValue: 'closureId', optionsText: 'closureName', columns: comboColumns, controlType: 'ComboBox', enable: true }]
                        
                });
            }

            closeWindowns(): void {
                nts.uk.ui.windows.close();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();

                //Get ClosureEmploy
                self.getClosureEmploy(self.startDate);

                return dfd.promise();
            }

        }

        //Dto used to insertDelArray.
        export class ClousureEmpAddDto {
            //Employ data contain closure ID
            empCdNameList: Array<EmpCdNameDto>;

            constructor(empCdNameList: Array<EmpCdNameDto>) {
                this.empCdNameList = empCdNameList;
            }
        }

        //Main-Dto ClosureEmployDto
        export class ClosureEmployDto {
            //Employ data contain closure ID
            empCdNameList: Array<EmpCdNameDto>;
            //Check box data
            closureCdNameList: Array<ClosureCdNameDto>;

            constructor(empCdNameList: Array<EmpCdNameDto>, closureCdNameList: Array<ClosureCdNameDto>) {
                this.empCdNameList = empCdNameList;
                this.closureCdNameList = closureCdNameList;
            }
        }

        //Gid data Sub-Dto EmpCdNameDto
        export class EmpCdNameDto {
            code: string;
            name: string;
            closureId: number;

            constructor(code: string, name: string, closureId: number) {
                this.code = code;
                this.name = name;
                this.closureId = closureId;
            }
        }

        //Combobox data Sub-Dto ClosureCdNameDto
        export class ClosureCdNameDto {
            closureId: number;
            closureName: string;

            constructor(closureId: number, closureName: string) {
                this.closureId = closureId;
                this.closureName = closureName;
            }
        }

    }
}