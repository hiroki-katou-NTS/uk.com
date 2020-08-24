module nts.uk.at.view.ksu001.ac.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        modeCompany: KnockoutObservable<boolean> = ko.observable(true);
        workplaceModeName : KnockoutObservable<String > = ko.observable(getText("Com_Workplace"));
        
        palletUnit: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText("Com_Company") },
            { code: 2, name: getText("Com_Workplace") }
        ]);
        selectedpalletUnit: KnockoutObservable<number> ;
        overwrite: KnockoutObservable<boolean> = ko.observable(true);

        dataSourceCompany: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        dataSourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        sourceCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        sourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedButtonTableCompany: KnockoutObservable<any> = ko.observable({});
        selectedButtonTableWorkplace: KnockoutObservable<any> = ko.observable({});

        textName: KnockoutObservable<string> = ko.observable(null);
        tooltip: KnockoutObservable<string> = ko.observable(null);
        selectedLinkButtonCom: KnockoutObservable<number> = ko.observable(0);
        selectedLinkButtonWkp: KnockoutObservable<number> = ko.observable(0);
        flag: boolean = true;
        dataToStick: any = null;
        listPageInfo : any;
        isFirstCom: boolean = true;
        isFirstOrg: boolean = true;

        btnSelectedCom = {};
        btnSelectedOrg = {};

        textButtonArrComPattern: KnockoutObservableArray<any> = ko.observableArray([]);

        textButtonArrWkpPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        listShiftWork: any[] = ko.observableArray([]);
        KEY : string = 'USER_INFOR';

        constructor() {
            let self = this;
            
            self.contextMenu = [
                { id: "openDialog", text: getText("KSU001_1705"), action: self.openDialogJB.bind(self) },
                { id: "openPopup", text: getText("KSU001_1706"), action: self.openPopup.bind(self) },
                { id: "delete", text: getText("KSU001_1707"), action: self.remove.bind(self) }
            ];
            
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);
                self.selectedpalletUnit = ko.observable(userInfor.shiftPalletUnit);
            }).ifEmpty((data) => {
                self.selectedpalletUnit = ko.observable(1);
            });
            
            self.overwrite.subscribe((newValue) => {
                if (newValue) {
                    $("#extable").exTable("stickOverWrite", true);
                } else {
                    $("#extable").exTable("stickOverWrite", false);
                }
            });
            
            self.selectedpalletUnit.subscribe((newValue) => {
                if (self.flag == false)
                    return;
                if (newValue) {
                    $("#extable").exTable("stickData", []);
                    uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                        let userInfor = JSON.parse(data);
                        userInfor.shiftPalletUnit = newValue;
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });

                    self.changeMode();
                }
            });

            self.selectedButtonTableCompany.subscribe((value) => {
                console.log(value);
                let indexBtnSelected = value.column + value.row * 10;
                let arrDataToStick = [];

                // get listShiftMaster luu trong localStorage
                let itemLocal = uk.localStorage.getItem(self.KEY);
                let userInfor = JSON.parse(itemLocal.get());
                let listShiftMasterSaveLocal = userInfor.shiftMasterWithWorkStyleLst;

                if (value.column == -1 || value.row == -1) {
                    $("#extable").exTable("stickData", arrDataToStick);
                } else {
                    let isMasterNotReg = false;
                    let mami = nts.uk.resource.getText('KSU001_94');
                    for (let i = 0; i < value.data.data.length; i++) {
                        let obj = value.data.data[i];
                        let shiftMasterName = obj.value.toString();
                        let shiftMasterCode = obj.shiftMasterCode;
                        if (shiftMasterName == mami) {
                            isMasterNotReg = true;
                        } else {
                            arrDataToStick.push(new ExCell('', '', '', '', '', '', shiftMasterName, '',obj.shiftMasterCode)); 
                        }
                    }
                    if (isMasterNotReg == true) {
                        arrDataToStick = [];
                    }
                    
                    $("#extable").exTable("stickData", arrDataToStick);
                    
                    // set color for cell
                    $("#extable").exTable("stickStyler", function(rowIdx, key, innerIdx, data) {
                        let workInfo = _.filter(listShiftMasterSaveLocal, function(o) { return o.shiftMasterCode === data.shiftCode; });
                        if (workInfo.length > 0) {
                            /**
                             *  1日休日系  ONE_DAY_REST(0)
                             *  午前出勤系 MORNING_WORK(1)
                             *  午後出勤系 AFTERNOON_WORK(2)
                             *  1日出勤系 ONE_DAY_WORK(3)
                             */
                            let workStyle = workInfo[0].workStyle;
                            if (workStyle == AttendanceHolidayAttr.FULL_TIME + '') {
                                return { textColor: "#0000ff" }; // color-attendance
                            }
                            if (workStyle == AttendanceHolidayAttr.MORNING+ '') {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.AFTERNOON+ '') {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.HOLIDAY+ '') {
                                return { textColor: "#ff0000" };// color-holiday
                            }
                            if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                                // デフォルト（黒）  Default (black)
                                return { textColor: "#000000" }
                            }
                        }
                    });
                }

                if (value.column !== -1 && value.row !== -1) {
                    uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                        let userInfor: any = JSON.parse(data);
                        userInfor.shiftPalletPositionNumberCom = { column: self.selectedButtonTableCompany().column, row: self.selectedButtonTableCompany().row };
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });
                }
            });

            self.selectedButtonTableWorkplace.subscribe((value) => {
                console.log(value);
                let indexBtnSelected = value.column + value.row * 10;
                let arrDataToStickWkp = [];
                // get listShiftMaster luu trong localStorage
                let itemLocal = uk.localStorage.getItem(self.KEY);
                let userInfor = JSON.parse(itemLocal.get());
                let listShiftMasterSaveLocal = userInfor.shiftMasterWithWorkStyleLst;
                let mami = nts.uk.resource.getText('KSU001_94');

                if (value.column == -1 || value.row == -1) {
                    $("#extable").exTable("stickData", arrDataToStickWkp);
                } else {
                    let isMasterNotReg = false;
                    for (let i = 0; i < value.data.data.length; i++) {
                        let obj = value.data.data[i];
                        let shiftMasterName = obj.value.toString();
                        let shiftMasterCode = obj.shiftMasterCode;
                        if (shiftMasterName == mami) {
                            isMasterNotReg = true;
                        } else {
                            arrDataToStickWkp.push(new ExCell('', '', '', '', '', '', shiftMasterName, '',obj.shiftMasterCode)); 
                        }
                    }
                    if (isMasterNotReg == true) {
                        arrDataToStickWkp = [];
                    }
                    
                    $("#extable").exTable("stickData", arrDataToStickWkp);
                    
                    // set color for cell
                    $("#extable").exTable("stickStyler", function(rowIdx, key, innerIdx, data) {
                        let workInfo = _.filter(listShiftMasterSaveLocal, function(o) { return o.shiftMasterCode === data.shiftCode; });
                        if (workInfo.length > 0) {
                            /**
                             *  1日休日系  ONE_DAY_REST(0)
                             *  午前出勤系 MORNING_WORK(1)
                             *  午後出勤系 AFTERNOON_WORK(2)
                             *  1日出勤系 ONE_DAY_WORK(3)
                             */
                            let workStyle = workInfo[0].workStyle;
                            if (workStyle == AttendanceHolidayAttr.FULL_TIME + '') {
                                return { textColor: "#0000ff" }; // color-attendance
                            }
                            if (workStyle == AttendanceHolidayAttr.MORNING + '') {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.AFTERNOON + '') {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.HOLIDAY + '') {
                                return { textColor: "#ff0000" };// color-holiday
                            }
                            if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                                // デフォルト（黒）  Default (black)
                                return { textColor: "#000000" }
                            }
                        }
                    });
                }

                if (value.column !== -1 && value.row !== -1) {
                    uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                        let userInfor: IUserInfor = JSON.parse(data);
                        userInfor.shiftPalletPositionNumberOrg = { column: self.selectedButtonTableWorkplace().column, row: self.selectedButtonTableWorkplace().row };
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });
                }
            });

            $("#tableButton1").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });

            $("#tableButton2").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });
            
        }
        
        /**
         */
        changeMode(): void {
            let self = this;
            if (self.selectedpalletUnit() === 1) {
                self.modeCompany(true);
                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor : IUserInfor = JSON.parse(data);
                    self.getDataComPattern(userInfor.shiftPalettePageNumberCom);
                }).ifEmpty((data) => {
                    let userInfor : IUserInfor = JSON.parse(data);
                    self.getDataComPattern(1);
                });
            } else {
                self.modeCompany(false);
                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor: IUserInfor = JSON.parse(data);
                    self.getDataWkpPattern(userInfor.shiftPalettePageNumberOrg);
                }).ifEmpty((data) => {
                    let userInfor: IUserInfor = JSON.parse(data);
                    self.getDataWkpPattern(1);
                });
            }
        }
        
        /**
         * handle init
         * change text of linkbutton
         * set data for datasource
         */
        handleInitCom(listPageInfo: any, listPattern: any, pageNumber: any): any {
            let self = this;
            self.modeCompany(true);
            self.listPageInfo = listPageInfo;
            //set default for listTextButton and dataSource
            self.dataSourceCompany([null, null, null, null, null, null, null, null, null, null]);
            self.textButtonArrComPattern([]);
            self.textButtonArrComPattern.valueHasMutated();
            let arr = [];
            for (let i = 0; i < listPageInfo.length; i++) {
                arr.push({ name: ko.observable(listPageInfo[i].pageName), id: listPageInfo[i].pageNumber, formatter: _.escape });
            }
            self.textButtonArrComPattern(arr);
            self.textButtonArrComPattern.valueHasMutated();
            
            // get listShiftMaster luu torng localStorage
            let itemLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(itemLocal.get());
            let listShiftMasterSaveLocal = userInfor.shiftMasterWithWorkStyleLst;
            
            self.updateDataSourceCompany(listPattern , listShiftMasterSaveLocal);
            
            let indexLinkBtnCom = self.indexOfPageSelected(listPageInfo , pageNumber);
            self.selectedLinkButtonCom(indexLinkBtnCom);
            self.clickLinkButton(null, ko.observable(indexLinkBtnCom));
        }
        
        updateDataSourceCompany(listShiftPalletCom, listShiftMasterSaveLocal) {
            let self = this;
            if(listShiftPalletCom.length == 0) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                self.sourceCompany(source);
            }
            for (let i = 0; i < listShiftPalletCom.length; i++) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                let mami = nts.uk.resource.getText('KSU001_94');
                //set data for dataSource
                _.each(listShiftPalletCom[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject: any = [];

                    _.forEach(pattItem.workPairSet, (wPSet) => {
                        let matchShiftWork = _.find(listShiftMasterSaveLocal, ["shiftMasterCode", wPSet.shiftCode != null ? wPSet.shiftCode : wPSet.workTypeCode]);
                        let value = "";
                        if (self.selectedpalletUnit() === 1) {
                            let shortName = (matchShiftWork != null) ?  matchShiftWork.shiftMasterName : mami;
                            value = shortName;
                            arrPairShortName.push(shortName);
                        } else {
                            let shortName = (matchShiftWork != null) ? matchShiftWork.shiftMasterName  : mami;
                            value = shortName;
                            arrPairShortName.push(shortName);
                        }
                        arrPairObject.push({
                            index: self.selectedpalletUnit() === 1 ? wPSet.order : wPSet.pairNo,
                            value: value,
                            shiftMasterCode: self.selectedpalletUnit() === 1 ? wPSet.shiftCode : wPSet.workTypeCode
                        });
                    });

                    let arrDataOfArrPairObject: any = [];
                    _.each(arrPairObject, (data) => {
                        arrDataOfArrPairObject.push(data.data);
                    });

                    let arrTooltipClone = _.clone(arrPairShortName);
                    for (let i = 7; i < arrTooltipClone.length; i += 7) {
                        arrPairShortName.splice(i, 0, 'lb');
                        i++;
                    }
                    let tooltip: string = arrPairShortName.join('→');
                    tooltip = tooltip.replace(/→lb/g, '\n');

                    //insert data to source
                    source.splice(pattItem.patternNo - 1, 1, { text: text, tooltip: tooltip, data: arrPairObject });
                });
                self.dataSourceCompany().splice(listShiftPalletCom[i].groupNo - 1, 1, source);
            }
        }

        private indexOfPageSelected(listPageInfo : any, shiftPalettePageNumber : any) {
            let index = _.findIndex(listPageInfo, function(o) { return o.pageNumber == shiftPalettePageNumber; });
            return index != -1 ? index : 0;
        }
        
        handleInitWkp(listPageInfo: any, listPattern: any, pageNumber: any): any {
            let self = this;
            self.modeCompany(false);
            self.listPageInfo = listPageInfo;
            //set default for listTextButton and dataSource
            self.dataSourceWorkplace([null, null, null, null, null, null, null, null, null, null]);
            self.textButtonArrComPattern([]);
            self.textButtonArrComPattern.valueHasMutated();
            
            self.textButtonArrWkpPattern([]);
            self.textButtonArrWkpPattern.valueHasMutated();
            let arr = [];
            for (let i = 0; i < listPageInfo.length; i++) {
                arr.push({ name: ko.observable(listPageInfo[i].pageName), id: listPageInfo[i].pageNumber, formatter: _.escape });
            }
            self.textButtonArrWkpPattern(arr);
            self.textButtonArrWkpPattern.valueHasMutated();
            
            // get listShiftMaster luu torng localStorage
            let itemLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(itemLocal.get());
            let listShiftMasterSaveLocal = userInfor.shiftMasterWithWorkStyleLst;

            self.updateDataSourceWorkplace(listPattern, listShiftMasterSaveLocal);
            
            let indexLinkBtnOrg = self.indexOfPageSelected(listPageInfo , pageNumber);
            self.selectedLinkButtonWkp(indexLinkBtnOrg);
            self.clickLinkButton(null, ko.observable(indexLinkBtnOrg));
        }
        
        updateDataSourceWorkplace(listShiftPalletWorkPlace, listShiftMasterSaveLocal) {
            let self = this;
            if(listShiftPalletWorkPlace.length == 0){
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                self.sourceWorkplace(source);    
            }
            for (let i = 0; i < listShiftPalletWorkPlace.length; i++) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                let mami = nts.uk.resource.getText('KSU001_94');
                //set data for dataSource
                _.each(listShiftPalletWorkPlace[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject = [];

                    _.forEach(pattItem.workPairSet, (wPSet) => {
                        let matchShiftWork = _.find(listShiftMasterSaveLocal, ["shiftMasterCode", wPSet.shiftCode != null ? wPSet.shiftCode : wPSet.workTypeCode]);
                        let value = "";
                        if (self.selectedpalletUnit() === 1) {
                            let shortName = (matchShiftWork != null) ? matchShiftWork.shiftMasterName  :  mami;
                            value = shortName;
                            arrPairShortName.push(shortName);
                        } else {
                            let shortName = (matchShiftWork != null) ? matchShiftWork.shiftMasterName  : mami;
                            value = shortName;
                            arrPairShortName.push(shortName);
                        }
                        arrPairObject.push({
                            index: self.selectedpalletUnit() === 1 ? wPSet.order : wPSet.pairNo,
                            value: value,
                            shiftMasterCode: self.selectedpalletUnit() === 1 ? wPSet.shiftCode : wPSet.workTypeCode
                        });
                    });

                    let arrDataOfArrPairObject: any = [];
                    _.each(arrPairObject, (data) => {
                        arrDataOfArrPairObject.push(data.data);
                    });

                    // screen JA must not set symbol for arrPairObject
                    // set tooltip
                    let arrTooltipClone = _.clone(arrPairShortName);
                    for (let i = 7; i < arrTooltipClone.length; i += 7) {
                        arrPairShortName.splice(i, 0, 'lb');
                        i++;
                    }
                    let tooltip: string = arrPairShortName.join('→');
                    tooltip = tooltip.replace(/→lb/g, '\n');

                    // Insert data to source
                    source.splice(pattItem.patternNo - 1, 1, { text: text, tooltip: tooltip, data: arrPairObject });
                });
                self.dataSourceWorkplace().splice(listShiftPalletWorkPlace[i].groupNo - 1, 1, source);
            }
        }

        /**
         * Click to link button
         */
        clickLinkButton(element: any, indexLinkBtn?: any): void {
            let self = this,
                source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
                indexBtn: number = indexLinkBtn();
            if (self.listPageInfo.length == 0)
                return;
            nts.uk.ui.block.grayout();
            let pageNumberSelected = self.listPageInfo[indexBtn].pageNumber;
            let dataLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(dataLocal.get());

            let param = {
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                shiftPalletUnit: self.selectedpalletUnit(),
                pageNumberCom: pageNumberSelected,
                pageNumberOrg: pageNumberSelected,
                workplaceId: userInfor.workplaceId,
            }

            service.getShiftPalletWhenChangePage(param).done((data) => {
                if (self.selectedpalletUnit() === 1) {
                    // link button has color gray when clicked
                    _.each($('#group-link-button-ja a.hyperlink'), (a) => {
                        $(a).removeClass('color-gray');
                        $(a).removeClass('background-linkbtn');
                    });
                    $($('#group-link-button-ja a.hyperlink')[indexBtn]).addClass('color-gray');
                    $($('#group-link-button-ja a.hyperlink')[indexBtn]).addClass('background-linkbtn');

                    let shiftPalletPositionNumberCom = {};
                    uk.localStorage.getItem(self.KEY).ifPresent((dataLocal) => {
                        let userInfor = JSON.parse(dataLocal);
                        userInfor.shiftPalettePageNumberCom = pageNumberSelected;
                        userInfor.shiftMasterWithWorkStyleLst = data.listShiftMaster;
                        shiftPalletPositionNumberCom = userInfor.shiftPalletPositionNumberCom;
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });
                    
                    //set sourceCompa  
                    let listPattern = data.targetShiftPalette.shiftPalletCom;
                    self.updateDataSourceCompany(listPattern , data.listShiftMaster);
                    self.sourceCompany(self.dataSourceCompany()[pageNumberSelected - 1] || source);
                    self.selectedLinkButtonCom(indexBtn);
                    
                    // select button Table
                    if (self.isFirstCom) {
                        let indexBtnSelected = shiftPalletPositionNumberCom.column + shiftPalletPositionNumberCom.row * 10;
                        let dataSourceOfBtnSelect = self.sourceCompany()[indexBtnSelected];
                        if (dataSourceOfBtnSelect.hasOwnProperty('data')) {
                            // trường hợp 
                            shiftPalletPositionNumberCom.data = dataSourceOfBtnSelect;
                            self.selectedButtonTableCompany(shiftPalletPositionNumberCom);
                        } else {
                            // trường hợp  
                            let x = _.filter(self.sourceCompany(), function(o) { return o.hasOwnProperty('data') });
                            if (x.length > 0) {
                                let indexc = _.indexOf(self.sourceCompany(), x[0]);
                                let obj = self.getRowColumnIndex(indexc);
                                shiftPalletPositionNumberCom.row = obj.row;
                                shiftPalletPositionNumberCom.column = obj.column;
                                shiftPalletPositionNumberCom.data = x[0];
                                self.selectedButtonTableCompany(shiftPalletPositionNumberCom);
                                _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                                    $($('.ntsButtonTableButton')[index]).removeClass('ntsButtonCellSelected');
                                });
                                $($('.ntsButtonTableButton')[indexc]).addClass('ntsButtonCellSelected');
                            }
                        }
                        self.isFirstCom = false;
                    } else {
                        let x = _.filter(self.sourceCompany(), function(o) { return o.hasOwnProperty('data') });
                        if (x.length > 0) {
                            let indexc = _.indexOf(self.sourceCompany(), x[0]);
                            let obj = self.getRowColumnIndex(indexc);
                            shiftPalletPositionNumberCom.row = obj.row;
                            shiftPalletPositionNumberCom.column = obj.column;
                            shiftPalletPositionNumberCom.data = x[0];
                            self.selectedButtonTableCompany(shiftPalletPositionNumberCom);
                            _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                                $($('.ntsButtonTableButton')[index]).removeClass('ntsButtonCellSelected');
                            });
                            $($('.ntsButtonTableButton')[indexc]).addClass('ntsButtonCellSelected');
                        }
                    }
                    nts.uk.ui.block.clear();

                } else if (self.selectedpalletUnit() === 2) {
                    // link button has color gray when clicked
                    _.each($('#group-link-button-ja a.hyperlink'), (a) => {
                        $(a).removeClass('color-gray');
                        $(a).removeClass('background-linkbtn');
                    });
                    $($('#group-link-button-ja a.hyperlink')[indexBtn]).addClass('color-gray');
                    $($('#group-link-button-ja a.hyperlink')[indexBtn]).addClass('background-linkbtn');

                    let shiftPalletPositionNumberOrg = {};
                    uk.localStorage.getItem(self.KEY).ifPresent((dataLocal) => {
                        let userInfor = JSON.parse(dataLocal);
                        userInfor.shiftPalettePageNumberOrg = pageNumberSelected;
                        userInfor.shiftMasterWithWorkStyleLst = data.listShiftMaster;
                        shiftPalletPositionNumberOrg = userInfor.shiftPalletPositionNumberOrg;
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });
                    //set sourceWorkplace
                    let listPattern = data.targetShiftPalette.shiftPalletWorkPlace;
                    self.updateDataSourceWorkplace(listPattern, data.listShiftMaster);
                    self.sourceWorkplace(self.dataSourceWorkplace()[pageNumberSelected - 1] || source);
                    self.selectedLinkButtonWkp(indexBtn);

                    // select button Table
                    let indexBtnSelectedOrg = shiftPalletPositionNumberOrg.column + shiftPalletPositionNumberOrg.row * 10;
                    if (self.isFirstOrg) {
                        let dataSourceOfBtnSelect = self.sourceWorkplace()[indexBtnSelectedOrg];
                        if (dataSourceOfBtnSelect.hasOwnProperty('data')) {
                            // trường hợp 
                            shiftPalletPositionNumberOrg.data = dataSourceOfBtnSelect;
                            self.selectedButtonTableWorkplace(shiftPalletPositionNumberOrg);
                        } else {
                            // trường hợp 
                            let x = _.filter(self.sourceWorkplace(), function(o) { return o.hasOwnProperty('data') });
                            if (x.length > 0) {
                                let indexc = _.indexOf(self.sourceWorkplace(), x[0]);
                                let obj = self.getRowColumnIndex(indexc);
                                shiftPalletPositionNumberOrg.row = obj.row;
                                shiftPalletPositionNumberOrg.column = obj.column;
                                shiftPalletPositionNumberOrg.data = x[0];
                                self.selectedButtonTableWorkplace(shiftPalletPositionNumberOrg);
                                _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                                    $($('.ntsButtonTableButton')[index]).removeClass('ntsButtonCellSelected');
                                });
                                $($('.ntsButtonTableButton')[indexc]).addClass('ntsButtonCellSelected');
                            }
                        }
                        self.isFirstOrg = false;
                    } else {
                        let x = _.filter(self.sourceWorkplace(), function(o) { return o.hasOwnProperty('data') });
                        if (x.length > 0) {
                            let indexc = _.indexOf(self.sourceWorkplace(), x[0]);
                            let obj = self.getRowColumnIndex(indexc);
                            shiftPalletPositionNumberOrg.row = obj.row;
                            shiftPalletPositionNumberOrg.column = obj.column;
                            shiftPalletPositionNumberOrg.data = x[0];
                            self.selectedButtonTableWorkplace(shiftPalletPositionNumberOrg);
                            _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                                $($('.ntsButtonTableButton')[index]).removeClass('ntsButtonCellSelected');
                            });
                            $($('.ntsButtonTableButton')[indexc]).addClass('ntsButtonCellSelected');
                        }
                    }
                }

                // set css table button
                _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                    if ($('.ntsButtonTableButton')[index].innerHTML == "+") {
                        $($('.ntsButtonTableButton')[index]).addClass('nowithContent');
                    } else {
                        $($('.ntsButtonTableButton')[index]).addClass('withContent');
                    }
                });
                nts.uk.ui.block.clear();
            }).fail(function() {
                nts.uk.ui.block.clear();
            });
        }
        
        getRowColumnIndex(indexBtnSelected: number) {
            if (indexBtnSelected < 10) {
                let obj = {row : 0 , column : indexBtnSelected};
                return obj;
            } else {
                let obj = {row : 1 , column : indexBtnSelected - 10};
                return obj;
            }
        }
        
        getShiftPalletWhenChangePage(pageNumber: number): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let dataLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(dataLocal.get());

            let param = {
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                shiftPalletUnit: self.selectedpalletUnit(),
                pageNumberCom: pageNumber,
                pageNumberOrg: pageNumber,
                workplaceId: userInfor.workplaceId,
            }

            service.getShiftPalletWhenChangePage(param).done((data) => {
                __viewContext.viewModel.viewA.saveShiftMasterToLocalStorage(data.listShiftMaster);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Open popup to change name button
         */
        openPopup(button) {}

        /**
         * decision change name button
         */
        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#tableButton").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip() });
        }

        /**
         * Close popup
         */
        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#tableButton").trigger("namechanged", undefined);
        }


        getDataComPattern(pageNumber): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let dataLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(dataLocal.get());
            let param = {
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                shiftPaletteWantGet: {
                    shiftPalletUnit: self.selectedpalletUnit(),
                    pageNumberCom: pageNumber,
                },
                workplaceId: userInfor.workplaceId,
                workplaceGroupId: ''
            }
            nts.uk.ui.block.grayout();
            service.getShiftPallets(param).done((data) => {
                self.handleInitCom(
                    data.listPageInfo,
                    data.targetShiftPalette.shiftPalletCom,
                    pageNumber);
                nts.uk.ui.block.clear();
                dfd.resolve();
            }).fail(function() {
                nts.uk.ui.block.clear();
                dfd.reject();
            });

            return dfd.promise();
        }

        /**
         */
        getDataWkpPattern(pageNumber): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let dataLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(dataLocal.get());
            let param = {
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                shiftPaletteWantGet: {
                    shiftPalletUnit: self.selectedpalletUnit(),
                    pageNumberOrg: pageNumber,
                },
                workplaceId: userInfor.workplaceId,
                workplaceGroupId: ''
            }
            nts.uk.ui.block.grayout();
            service.getShiftPallets(param).done((data) => {
                self.handleInitWkp(
                    data.listPageInfo,
                    data.targetShiftPalette.shiftPalletWorkPlace,
                    pageNumber);
                nts.uk.ui.block.clear();
                dfd.resolve();
            }).fail(function() {
                nts.uk.ui.block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Open dialog JB
         */
        openDialogJB1(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let dataLocal = uk.localStorage.getItem(self.KEY);
            let userInfor : IUserInfor = JSON.parse(dataLocal.get());
            
            setShared('dataForJB', {
                selectedTab: self.selectedpalletUnit() == 1 ? 'company' : 'workplace',
                workplaceName: self.workplaceModeName,
                workplaceCode: '',
                workplaceId: self.selectedpalletUnit() === 1 ? null : userInfor.workplaceId,
                listWorkType: __viewContext.viewModel.viewAB.listWorkType(),
                listWorkTime: __viewContext.viewModel.viewAB.listWorkTime(),
                selectedLinkButton: self.selectedpalletUnit() === 1 ? userInfor.shiftPalettePageNumberCom - 1 : userInfor.shiftPalettePageNumberOrg - 1,
                // listCheckNeededOfWorkTime for JA to JA send to JB
                listCheckNeededOfWorkTime: __viewContext.viewModel.viewA.listCheckNeededOfWorkTime(),
                overwrite : self.overwrite()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let pageNumber: any = getShared("dataFromJA").selectedLinkButton + 1;
                if (self.selectedpalletUnit() === 1) {
                    self.modeCompany(true);
                    self.getDataComPattern(pageNumber).done(() => {
                        console.log("get data com done");
                    });
                } else {
                    self.modeCompany(false);
                    self.getDataWkpPattern(pageNumber).done(() => {
                        console.log("get data workplace done");
                    });
                }
                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        /**
         * Open dialog JB
         */
        openDialogJB(evt, data): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            self.textName(data ? data.text : null);
            self.tooltip(data ? data.tooltip : null);
            setShared("dataForJB", {
                text: self.textName(),
                tooltip: self.tooltip(),
                data: data ? data.data : null,
                textDecision: getText("KSU001_924"),
                listCheckNeededOfWorkTime: __viewContext.viewModel.viewA.listCheckNeededOfWorkTime()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let data = getShared("dataFromJB");
                if (data) {
                    self.textName(data.text);
                    self.tooltip(data.tooltip);
                    let dataBasicSchedule = _.map(data.data, 'data');
                    //set symbol for object
                    $.when(__viewContext.viewModel.viewA.setDataToDisplaySymbol(dataBasicSchedule)).done(() => {
                        dfd.resolve({ text: self.textName(), tooltip: self.tooltip(), data: data.data });
                        self.refreshDataSource();
                        // neu buttonTable do dang dc select, set lai data cho dataToStick 
                        if (self.indexBtnSelected == $(evt).attr('data-idx')) {
                            $("#extable").exTable("stickData", dataBasicSchedule);
                        }
                    });
                }
            });
            return dfd.promise();
        }

        /**
         * remove button on table
         */
        remove(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
                self.refreshDataSource();
            }, 10);

            return dfd.promise();
        }

        /**
         * refresh dataSource
         */
        refreshDataSource(): void {
            let self = this;
            if (self.selectedpalletUnit() === 1) {
                self.dataSourceCompany()[self.selectedLinkButtonCom()] = self.sourceCompany();
            } else {
                self.dataSourceWorkplace()[self.selectedLinkButtonWkp()] = self.sourceWorkplace();
            }
        }
    }
    class ExCell {
        workTypeCode: string;
        workTypeName: string;
        workTimeCode: string;
        workTimeName: string;
        shiftName: string;
        startTime: any;
        endTime: any;
        workStyle: number;
        shiftCode: string;
        
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime?: string, endTime?: string, shiftName?: any, workStyle? : any, shiftCode? : any) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.workTimeCode = workTimeCode;
            this.workTimeName = workTimeName;
            this.shiftName = shiftName !== null ? shiftName : '';
            this.startTime = ( startTime == undefined || startTime == null ) ? '' : startTime;
            this.endTime = ( endTime == undefined || endTime == null ) ? '' : endTime;
            this.workStyle = workStyle !== null ? workStyle : '';
            this.shiftCode = shiftName !== null ? shiftCode : '';
        }
    }

    interface IUserInfor {
        disPlayFormat: string;
        backgroundColor: number; // 背景色
        achievementDisplaySelected: number;
        shiftPalletUnit: number;
        shiftPalettePageNumberCom: number;
        shiftPalletPositionNumberCom: {};
        shiftPalettePageNumberOrg: number;
        shiftPalletPositionNumberOrg: {};
        gridHeightSelection: number;
        heightGridSetting: number;
        workplaceId: string;
        workplaceGroupId: string;
        workPlaceName: string;
        workType: {};
        workTime: {};
        updateMode: string; // updatemode cua grid
        startDate: string;
        endDate: string;
        shiftMasterWithWorkStyleLst: Array<IShiftMasterMapWithWorkStyle>;
    }

    interface IShiftMasterMapWithWorkStyle {
        companyId: string;
        shiftMasterName: string;
        shiftMasterCode: string;
        color: string;
        remark: string;
        workTypeCode: string;
        workTimeCode: string;
        workStyle: string;
    }

    enum AttendanceHolidayAttr {
        FULL_TIME = 3, //(3, "１日出勤系"),
        MORNING = 1, //(1, "午前出勤系"),
        AFTERNOON = 2, //(2, "午後出勤系"),
        HOLIDAY = 0, //(0, "１日休日系");
    }
}