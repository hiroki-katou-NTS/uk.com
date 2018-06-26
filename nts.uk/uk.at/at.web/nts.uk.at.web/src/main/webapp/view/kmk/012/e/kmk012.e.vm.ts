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
            //Data respone from Ws.
            closureEmployDto: ClosureEmployDto;

            constructor() {
                let self = this;

                //List clousureEmpAddDto,  clousureEmpAddDto.empCdNameList to insert del all.
                self.clousureEmpAddDto = new ClousureEmpAddDto(new Array<EmpCdNameDto>());

                //Init list data on grid.
                self.items = new Array<any>();

                //Init data combobox
                self.closureEmployDto = new ClosureEmployDto(null, null);
            }

            //Insert del in server.
            insertDelArray(source: any) {
                let self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                //Only get item when closureId != -1
                _.forEach(source, function(item) {
                    if (item.closureId != -1) {
                        self.clousureEmpAddDto.empCdNameList.push(item);
                    } else {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_915" });
                        self.clousureEmpAddDto.empCdNameList =  new Array<EmpCdNameDto>();
                        return false;
                    }
                });
                if(self.clousureEmpAddDto.empCdNameList.length != 0){
                     //Insert del in server.
                    service.insertDelArray(self.clousureEmpAddDto).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        nts.uk.ui.block.clear();    
                        //Clear data when add success. To fix bug add duplicate item when not refresh web page.
                        self.clousureEmpAddDto.empCdNameList = new Array<EmpCdNameDto>();
                        
                        dfd.resolve();
    
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                        nts.uk.ui.block.clear();    
                    });
                }
               

                return dfd.promise();
            }

            getClosureEmploy() {
                let self = this;
                var dfd = $.Deferred();

                //Get ClosureEmploy with param: startDate and return: data 
                service.getClosureEmploy().done(function(data) {
                    self.closureEmployDto = new ClosureEmployDto(data.empCdNameList, data.closureCdNameList);

                    //Init data for list item on grid.
                    self.items = self.closureEmployDto.empCdNameList;
                    //If closureID = null init value -1
                    self.items = _.map(self.items, function(item) {
                        if (item.closureId == null) {
                            item.closureId = -1;
                            return item;
                        } else {
                            return item;
                        }
                    });

                    dfd.resolve();
                });

                return dfd.promise();
            }

            closeWindowns(): void {
                nts.uk.ui.windows.close();
            }

        }

        //Dto ClousureEmpAddDto used to add.
        export class ClousureEmpAddDto {
            //Employ data contain closure ID
            empCdNameList: Array<EmpCdNameDto>;

            constructor(empCdNameList: Array<EmpCdNameDto>) {
                this.empCdNameList = empCdNameList;
            }
        }

        //Main-Dto ClosureEmployDto used to list.
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

        //Gid data Sub-Dto EmpCdNameDto used to list.
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
            id: number;
            name: string;

            constructor(closureIdMain: number, closureName: string) {
                this.id = closureIdMain;
                this.name = closureName;
            }
        }

    }
}