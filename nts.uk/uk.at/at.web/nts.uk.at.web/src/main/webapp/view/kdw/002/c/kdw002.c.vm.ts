module nts.uk.at.view.kdw002.c {
    import alert = nts.uk.ui.dialog.alert;
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            listAttdItem: Array<any>;
            dailyServiceTypeControl: KnockoutObservable<DailyAttendanceItemAuth>;
            columns: Array<any>;
            //monthly
            listAttdMonthlyItem: Array<any>;

            bussinessCodeItems: KnockoutObservableArray<BusinessType>;
            bussinessColumn: KnockoutObservableArray<any>;
            currentRoleId: KnockoutObservable<any>;
            txtSearch: KnockoutObservable<string>;
            //isDaily
            isDaily :boolean;
            sideBar :  KnockoutObservable<number>;
            //datasource
            datasources :KnockoutObservableArray<any>;
            selectedList: any;
            
            listAttFullData  : any;
            listAttFullDataClone  : any;
            
            constructor(dataShare:any) {
                var self = this;
                self.bussinessCodeItems = ko.observableArray([]);
                self.listAttdItem = [];
                self.dailyServiceTypeControl = ko.observable(null);
                self.isDaily = dataShare.ShareObject;
                self.sideBar =  ko.observable(1);
                if(!self.isDaily){
                    self.sideBar(2);
                } 
                //
                self.datasources = ko.observableArray([]);
                self.selectedList = ko.observableArray([]);
                
                self.listAttFullData = ko.observableArray([]);
                self.listAttFullDataClone = ko.observableArray([]);
                //monthly
                self.listAttdMonthlyItem = [];

                this.bussinessColumn = ko.observableArray([
                    { headerText: 'ID', key: 'roleId', width: 100, hidden: true },
                    { headerText: getText('KDW002_12'), key: 'roleCode', width: 100 },
                    { headerText: getText('KDW002_4'), key: 'roleName', width: 150, formatter: _.escape },
                ]);

                self.currentRoleId = ko.observable('');
                self.currentRoleId.subscribe(roleId => {
                    self.currentRoleId(roleId);
                    _.defer(() => nts.uk.ui.block.invisible());
                    
                    var useTemplate = "<input tabindex='-1' type='checkbox' {{if ${toUse} }} checked {{/if}} onclick='useChanged(this, ${itemDailyID},${userCanUpdateAtr})' />";
                    var youCanChangeItTemplate = "<input tabindex='-1' type='checkbox' {{if ${youCanChangeIt} }} checked {{/if}} onclick='youCanChangeItChanged(this, ${itemDailyID})' />";
                    var canBeChangedByOthersTemplate = "<input tabindex='-1' type='checkbox' {{if ${canBeChangedByOthers} }} checked {{/if}} onclick='canBeChangedByOthersChanged(this, ${itemDailyID})' />";

                    var useHeader = "<input  tabindex='-1' type='checkbox' id = 'useCheckAll' onclick='useHeaderChanged(this)'/> ";
                    var youCanChangeItHeader =  "<input  tabindex='-1' type='checkbox' id = 'youCanCheckAll' onclick='youCanChangeItHeaderChanged(this)'/> ";
                    var canBeChangedByOthersHeader = "<input  tabindex='-1'  type='checkbox' id = 'otherCheckAll' onclick='canBeChangedByOthersHeaderChanged(this)'/> ";
                    self.columns = ko.observableArray([
                        { headerText: '', key: 'itemDailyID', width: 1, hidden: true },
                        { headerText: getText('KDW002_3'), key: 'displayNumber', width: 70  , formatter: _.escape },
                        { headerText: getText('KDW002_4'), key: 'itemDailyName', width: 220  , formatter: _.escape},
                        { headerText: useHeader + getText('KDW002_5'), key: 'toUse', width: 120, template: useTemplate },
                        { headerText: youCanChangeItHeader + getText('KDW002_6'), key: 'youCanChangeIt', width: 120, template: youCanChangeItTemplate },
                        { headerText: canBeChangedByOthersHeader + getText('KDW002_7'), key: 'canBeChangedByOthers', width: 165, template: canBeChangedByOthersTemplate },
                        { headerText: '', key: 'userCanUpdateAtr', width: 1, hidden: true },

                    ]);


                    let dfd:any;
                    if(roleId){
                        if(self.isDaily){
                             dfd = self.getDailyAttdItemByRoleID(roleId);
                        }else{
                             dfd = self.getMonthlyAttdItemByRoleID(roleId)
                        }
                    }
                    $.when(dfd).done(
                        (DailyServiceTypeControls) => {
                            
                            $('#useCheckAll').prop('checked', false);
                            $('#youCanCheckAll').prop('checked', false);
                            $('#otherCheckAll').prop('checked', false);
                            if (!nts.uk.util.isNullOrUndefined(DailyServiceTypeControls)) {
                                self.datasources(self.dailyServiceTypeControl().displayAndInput);
                                $("#grid").igGrid({
                                    primaryKey: "itemDailyID",
                                    height: 400,
                                    dataSource: self.datasources(),
//                                    autoGenerateColumns: false,
//                                    alternateRowStyles: false,
                                    dataSourceType: "json",
                                    autoCommit: true,
                                    tabIndex: -1,
                                    //virtualization: true,
                                    rowVirtualization: false,
                                  virtualizationMode: "continuous",
//                                    virtualizationMode: "fixed",
                                    columns: self.columns(),
                                    features: [
                                        {
                                            name: "Updating",
                                            showDoneCancelButtons: false,
                                            enableAddRow: false,
                                            enableDeleteRow: false,
                                            editMode: 'cell',
                                            columnSettings: [
                                                { columnKey: "itemDailyID", hidden: true },
                                                { columnKey: "itemDailyName", readOnly: true },
                                                { columnKey: "toUse", readOnly: true },
                                                { columnKey: "youCanChangeIt", readOnly: true },
                                                { columnKey: "canBeChangedByOthers", readOnly: true },
                                                { columnKey: "userCanUpdateAtr", hidden: true }
                                            ]
                                        },
                                        {
                                            name: "Selection",
                                            mode: "row",
                                            multipleSelection: false,
                                            touchDragSelect: false, // this is true by default
                                            multipleCellSelectOnClick: false
                                            
                                        }
                                    ],
                                    avgRowHeight: "26px"
                                });
                                 $("#grid").setupSearchScroll("igGrid", false);
                                 $("#grid").ntsGridList("setupScrollWhenBinding"); 
                                let lengthData = self.dailyServiceTypeControl().displayAndInput.length;
                                for (let i = 0; i < lengthData; i++) {
                                    if (!self.dailyServiceTypeControl().displayAndInput[i].userCanUpdateAtr || !self.dailyServiceTypeControl().displayAndInput[i].toUse) {
                                        if (!self.dailyServiceTypeControl().displayAndInput[i].userCanUpdateAtr) {
                                            $("#grid").igGridUpdating("setCellValue", self.dailyServiceTypeControl().displayAndInput[i].itemDailyID, "canBeChangedByOthers", false);
                                        }
                                        var rowId = self.dailyServiceTypeControl().displayAndInput[i].itemDailyID;
                                        var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
                                        var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
                                        cellYouCanChangeIt.addClass('readOnlyColorIsUse');
                                        cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
                                        cellYouCanChangeIt.children().prop("disabled", true);
                                        cellCanBeChangedByOthers.children().prop("disabled", true);


                                    }
                                }
                            }
                            let listData = self.dailyServiceTypeControl().displayAndInput;
                            let notUseExist = _.find(listData, function(e){ return !e.toUse });
                            if(nts.uk.util.isNullOrUndefined(notUseExist)){
                                $("#useCheckAll").prop('checked', true);
                            }else{
                                $("#useCheckAll").prop('checked', false);
                            }
                            
                            let notYouCanCheckAll = true;
                            for(let i =0;i<listData.length;i++){
                                if(listData[i].toUse ==true && listData[i].userCanUpdateAtr == 1){
                                    if(!listData[i].youCanChangeIt){
                                        notYouCanCheckAll =false;
                                        break;
                                    }
                                }
                            }
                            $("#youCanCheckAll").prop('checked', notYouCanCheckAll);
                            
                            let notOtherCheckAll = true;
                            for(let i =0;i<listData.length;i++){
                                if(listData[i].toUse ==true && listData[i].userCanUpdateAtr == 1){
                                    if(!listData[i].canBeChangedByOthers){
                                        notOtherCheckAll =false;
                                        break;
                                    }
                                }
                            }
                            $("#otherCheckAll").prop('checked', notOtherCheckAll);
                            
                            nts.uk.ui.block.clear();
                        }
                    );
                });

                self.txtSearch = ko.observable("");
            }
            
            jumpToHome(sidebar): void {
                let self = this;
                nts.uk.request.jump("/view/kdw/006/a/index.xhtml", { ShareObject : sidebar() });
            }
            
            copy(): void {
                var self = this;
//                var bussinessCodeItems = self.bussinessCodeItems();
//                var currentRoleId = self.currentRoleId();
//                var bussinessCodeItem = _.find(self.bussinessCodeItems(), { businessTypeCode: self.currentRoleId() });
//                var businessTypeName = bussinessCodeItem.roleName;
//                var data = {
//                    code: currentRoleId, name: businessTypeName, bussinessItems: bussinessCodeItems
//                }
//                let object: IObjectDuplication = {
//                    code: bussinessCodeItem,
//                    name: businessTypeName,
//                    targetType: 1,
//                    itemListSetting: bussinessCodeItems,
//                    baseDate: moment(self.baseDate()).toDate()
//                };
//                nts.uk.ui.windows.sub.modal("com","/view/cdl/023/a/index.xhtml");
//                
//                nts.uk.ui.windows.setShared("KDW002_B_AUTHORITYTYPE", data);
            }
            


            startPage(): JQueryPromise<any> {
                let self = this,
                    typeInput = TypeInput.DAILY;
                let dfd = $.Deferred();
                 if(!self.isDaily){
                    typeInput = TypeInput.MONTHLY;
                } 
                let dtdGetNameAttItemByType = self.getNameAttItemByType(typeInput);
                $.when(dtdGetNameAttItemByType).done(() => {
                    service.getEmpRole().done(empRoles => {
                        if (!nts.uk.util.isNullOrUndefined(empRoles) && empRoles.length > 0) {
                            let bussinessCodeItems = [];
                            empRoles.forEach(empRole => {
                                bussinessCodeItems.push(new BusinessType(empRole));
                                //   self.bussinessCodeItems.push(new BusinessType(businessType));
                            });
                            var bTypes = _.sortBy(bussinessCodeItems, 'roleCode');
                            self.bussinessCodeItems(bTypes);
                            var businessTypeCode = bTypes[0].roleId;
                            self.currentRoleId(businessTypeCode);
                        }
                        dfd.resolve();
                        $("#submitDataId").focus();
                    });
                }).always(() => {
                    dfd.resolve();
                })
                
                return dfd.promise();
            }

            getNameAttItemByType(typeInput : number){
                let self = this;
                let dfd = $.Deferred();
                service.getNameAttItemByType(typeInput).done(function(data) {
                    self.listAttFullData(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            //get Daily Attd Item By Role ID
            getDailyAttdItemByRoleID(roleID: string) {
                let self = this;
                let dfd = $.Deferred();
                let startTime: number = performance.now();
                service.getDailyAttItemNew(roleID).done(function(data) {
                    console.log("get setting by role: " + (performance.now() - startTime));
                    let listDefault: Array<DisplayAndInputControl> = [];
                    self.listAttFullDataClone(_.cloneDeep(self.listAttFullData()));
                    _.each(self.listAttFullDataClone(), attFullData => {
                        for(let i=0;i<data.length;i++){
                            if(attFullData.attendanceItemId == data[i].attendanceItemId){
                                attFullData.authority = data[i].authority; 
                                break;
                            }    
                        }
                            
                        listDefault.push(DisplayAndInputControl.fromApp(attFullData));
                    });
                    /*if (nts.uk.util.isNullOrUndefined(data)) {
                        if (self.listAttdItem.length != 0) {
                            for (let i = 0; i < self.listAttdItem.length; i++) {
                                listDefault.push(
                                    new DisplayAndInputControl(
                                        self.listAttdItem[i].attendanceItemId,
                                        self.listAttdItem[i].attendanceName,
                                        self.listAttdItem[i].displayNumber,
                                        self.listAttdItem[i].userCanUpdateAtr,
                                        true,
                                        false,
                                        false));
                            }
                        }

                    } else {
                        if (self.listAttdItem.length != 0) {
                            for (let j = 0; j < data.displayAndInput.length; j++) {
                                for (let i = 0; i < self.listAttdItem.length; i++) {
                                    if (data.displayAndInput[j].itemDailyID == self.listAttdItem[i].attendanceItemId) {
                                        listDefault.push(
                                            new DisplayAndInputControl(
                                                self.listAttdItem[i].attendanceItemId,
                                                self.listAttdItem[i].attendanceName,
                                                self.listAttdItem[i].displayNumber,
                                                self.listAttdItem[i].userCanUpdateAtr,
                                                data.displayAndInput[j].toUse,
                                                data.displayAndInput[j].canBeChangedByOthers,
                                                data.displayAndInput[j].youCanChangeIt));
                                        break;
                                    }//end if        
                                }//end for 2
                            }//end for 1
                        }//end if

                    }//end else to*/
                    self.dailyServiceTypeControl(
                        new DailyAttendanceItemAuth("", roleID, _.sortBy(listDefault, ['displayNumber']))
                    );
                    console.log("convert object: " + (performance.now() - startTime));
                    dfd.resolve(self.dailyServiceTypeControl());
                });
                return dfd.promise();
            }
            
            //get monthly Attd Item By Role ID
            getMonthlyAttdItemByRoleID(roleID: string) {
                let self = this;
                let dfd = $.Deferred();
                service.getMontlyAttItemNew(roleID).done(function(data) {
                    let listDefault: Array<DisplayAndInputControl> = [];
//                    _.each(data, item => {
//                        listDefault.push(DisplayAndInputControl.fromApp(item));
//                    })
                    self.listAttFullDataClone(_.cloneDeep(self.listAttFullData()));
                    _.each(self.listAttFullDataClone(), attFullData => {
                        for(let i=0;i<data.length;i++){
                            if(attFullData.attendanceItemId == data[i].attendanceItemId){
                                attFullData.authority = data[i].authority;
                                break;
                            }    
                        }
                            
                        listDefault.push(DisplayAndInputControl.fromApp(attFullData));
                    });
                    /*if (nts.uk.util.isNullOrUndefined(data)) {
                        
                        
                        if (self.listAttdMonthlyItem.length != 0) {
                            for (let i = 0; i < self.listAttdMonthlyItem.length; i++) {
                                listDefault.push(
                                    new DisplayAndInputControl(
                                        self.listAttdMonthlyItem[i].attendanceItemId,
                                        self.listAttdMonthlyItem[i].attendanceItemName,
                                        self.listAttdMonthlyItem[i].attendanceItemDisplayNumber,
                                        self.listAttdMonthlyItem[i].userCanUpdateAtr,
                                        true,
                                        false,
                                        false));
                            }
                        }

                    } else {
                        
                        if (self.listAttdMonthlyItem.length != 0) {
                            for (let j = 0; j < data.listDisplayAndInputMonthly.length; j++) {
                                for (let i = 0; i < self.listAttdMonthlyItem.length; i++) {
                                    if (data.listDisplayAndInputMonthly[j].itemMonthlyId == self.listAttdMonthlyItem[i].attendanceItemId) {
                                        listDefault.push(
                                            new DisplayAndInputControl(
                                                self.listAttdMonthlyItem[i].attendanceItemId,
                                                self.listAttdMonthlyItem[i].attendanceItemName,
                                                self.listAttdMonthlyItem[i].attendanceItemDisplayNumber,
                                                self.listAttdMonthlyItem[i].userCanUpdateAtr,
                                                data.listDisplayAndInputMonthly[j].toUse,
                                                data.listDisplayAndInputMonthly[j].canBeChangedByOthers,
                                                data.listDisplayAndInputMonthly[j].youCanChangeIt));
                                        break;
                                    }//end if        
                                }//end for 2
                            }//end for 1
                        }//end if

                    }//end else to*/
                    self.dailyServiceTypeControl(
                        new DailyAttendanceItemAuth("", roleID, _.sortBy(listDefault, ['displayNumber']))
                    );
                    
                    dfd.resolve(self.dailyServiceTypeControl());
                });
                return dfd.promise();
            }
            
            /*
            //daily
            getListDailyAttdItem() {
                let self = this;
                let dfd = $.Deferred();
                service.getListDailyAttdItem().done(function(data) {
                    let listAttdID = _.map(data,item =>{return item.attendanceItemId; });
                    service.getNameDaily(listAttdID).done(function(dataNew) {
                        for(let i =0;i<data.length;i++){
                            for(let j = 0;j<=dataNew.length; j++){
                                if(data[i].attendanceItemId == dataNew[j].attendanceItemId ){
                                    data[i].attendanceName = dataNew[j].attendanceItemName;
                                    break;
                                }  
                            }    
                        }
                        
                        self.listAttdItem = data; 
                        dfd.resolve();   
                    });
                });
                return dfd.promise();
            }
            //monthly
            getListMonthlyAttdItem() {
                let self = this;
                let dfd = $.Deferred();
                service.getListMonthlyAttdItem().done(function(data) {
                    let listAttdID = _.map(data,item =>{return item.attendanceItemId; });
                    service.getNameMonthly(listAttdID).done(function(dataNew) {
                        for(let i =0;i<data.length;i++){
                            for(let j = 0;j<=dataNew.length; j++){
                                if(data[i].attendanceItemId == dataNew[j].attendanceItemId ){
                                    data[i].attendanceItemName = dataNew[j].attendanceItemName;
                                    break;
                                }  
                            }    
                        }
                        self.listAttdMonthlyItem = data; 
                        dfd.resolve();
                        });   
                });
                return dfd.promise();
            }*/

            submitData(): void {
                var self = this;
                var dataSource = $('#grid').data('igGrid').dataSource;
                self.dailyServiceTypeControl().displayAndInput = dataSource.transformedData('afterfilteringandpaging');
                nts.uk.ui.block.invisible();
                if(self.isDaily){
                    service.updateDailyAttdItem(self.dailyServiceTypeControl()).done(x => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(function() {
                            self.getDailyAttdItemByRoleID(self.dailyServiceTypeControl().authorityDailyId).done(function(){
                                $("#grid").igGrid("destroy")
                                self.currentRoleId(self.dailyServiceTypeControl().authorityDailyId);
                                self.currentRoleId.valueHasMutated();
                            });
                            
                        });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }else{
                    let dataMonthly = dataSource.transformedData('afterfilteringandpaging');
                    let listDisplayMonthly = [];
                    if(dataMonthly.length >0){
                        for(let i = 0;i<dataMonthly.length;i++){
                            listDisplayMonthly.push(
                                new DisplayAndInputMontly(
                                    dataMonthly[i].itemDailyID,
                                    dataMonthly[i].itemDailyName,
                                    dataMonthly[i].displayNumber,
                                    dataMonthly[i].userCanUpdateAtr,
                                    dataMonthly[i].toUse,
                                    dataMonthly[i].canBeChangedByOthers,
                                    dataMonthly[i].youCanChangeIt
                                    )
                                );
                        }
                    }
                    
                    let monthly = new MonthlyAttendanceItemAuth("",self.dailyServiceTypeControl().authorityDailyId,listDisplayMonthly);
                    service.updateMonthlyAttdItem(monthly).done(x => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(function() {
                            self.getMonthlyAttdItemByRoleID(monthly.authorityMonthlyId).done(function(){
                            ///var temp = $('#grid').igGrid("option","dataSource", self.dailyServiceTypeControl().displayAndInput)
                            $("#grid").igGrid("destroy")
                            self.currentRoleId(monthly.authorityMonthlyId);
                            self.currentRoleId.valueHasMutated();
                            });
                        });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                    
                }
            }

            navigateView(): void {
                let self = this;
                var path = "/view/kdw/006/a/index.xhtml";
                href(path);
            }

            searchData(): void {
                var self = this;
                if (self.txtSearch().length === 0) {
                    alert("検索キーワードを入力してください");
                    return;
                }
                var dataSource = $('#grid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');
                var rowIndexSelected = $('#grid').igGridSelection("selectedRow");
                var index = 0;
                if (typeof (rowIndexSelected) != 'undefined' && rowIndexSelected != null) {
                    if ((rowIndexSelected.index + 1) == filteredData.length) {
                        index = 0;
                    } else {
                        index = rowIndexSelected.index + 1;
                    }
                }
                var keynotExist = true;
                var i;
                var l = filteredData.length;
                for (i = index; i < l; i++) {
                    var rowId = filteredData[i].itemDailyID;
                    var nameValue = filteredData[i].attendanceItemName;
                    var idValue = rowId.toString();

                    if (_.includes(idValue, self.txtSearch()) || _.includes(nameValue, self.txtSearch())) {
                        $('#grid').igGridSelection('selectRow', i);
                        $('#grid').igGrid("virtualScrollTo", i);
                        keynotExist = false;
                        break;
                    }
                }
                if (keynotExist) {
                    for (i = 0; i < index; i++) {
                        var rowId = filteredData[i].itemDailyID;
                        var nameValue = filteredData[i].attendanceItemName;
                        var idValue = rowId.toString();
                        if (_.includes(idValue, self.txtSearch()) || _.includes(nameValue, self.txtSearch())) {
                            $('#grid').igGridSelection('selectRow', i);
                            $('#grid').igGrid("virtualScrollTo", i);
                            keynotExist = false;
                            break;
                        }
                    }
                }
                if (keynotExist) {
                    alert("該当する項目が見つかりませんでした");
                }
            }
        }

        interface IBusinessType {
            roleId: string;
            roleName: string;
        }

        class BusinessType {
            roleId: string;
            roleCode: string;
            roleName: string;
            constructor(param: IBusinessType) {
                let self = this;
                self.roleId = param.roleId;
                self.roleCode = param.roleCode;
                self.roleName = param.roleName;
            }
        }
        interface IDailyServiceTypeControl {
            attendanceItemId: number;
            authorityId: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
        }

        class DailyServiceTypeControl {
            attendanceItemId: number;
            authorityId: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
            constructor(param: IDailyServiceTypeControl) {
                let self = this;
                self.attendanceItemId = param.attendanceItemId;
                self.authorityId = param.authorityId;
                self.youCanChangeIt = param.youCanChangeIt;
                self.canBeChangedByOthers = param.canBeChangedByOthers;
                self.use = param.use;
            }
        }
        //class daily
        class DailyAttendanceItemAuth {
            companyID: string;
            authorityDailyId: string;
            displayAndInput: Array<DisplayAndInputControl>;
            constructor(companyID: string, authorityDailyId: string,
                displayAndInput: Array<DisplayAndInputControl>) {
                this.companyID = companyID;
                this.authorityDailyId = authorityDailyId;
                this.displayAndInput = displayAndInput;
            };
        }
        class DisplayAndInputControl {
            itemDailyID: number;
            itemDailyName: string;
            displayNumber: number;
            userCanUpdateAtr: number;
            toUse: boolean;
            canBeChangedByOthers: boolean;
            youCanChangeIt: boolean;
            constructor(){
            }
            static fromApp(app) {
                let dto = new DisplayAndInputControl();
                dto.itemDailyID = app.attendanceItemId;
                dto.itemDailyName = app.attendanceItemName;
                dto.displayNumber = app.attendanceItemDisplayNumber;
                dto.userCanUpdateAtr = app.userCanUpdateAtr;
                if (app.authority != null) {
                    dto.toUse = app.authority.toUse;
                    dto.canBeChangedByOthers = app.authority.canBeChangedByOthers;
                    dto.youCanChangeIt = app.authority.youCanChangeIt;
                } else {
                    dto.toUse = true;
                    dto.canBeChangedByOthers = false;
                    dto.youCanChangeIt = false;
                }
                return dto;
            }
        }
        //monthly
        //
        class MonthlyAttendanceItemAuth {
            companyID: string;
            authorityMonthlyId: string;
            listDisplayAndInputMonthly: Array<DisplayAndInputMontly>;
            constructor(companyID: string, authorityMonthlyId: string,
                listDisplayAndInputMonthly: Array<DisplayAndInputMontly>) {
                this.companyID = companyID;
                this.authorityMonthlyId = authorityMonthlyId;
                this.listDisplayAndInputMonthly = listDisplayAndInputMonthly;
            };
        }
        class DisplayAndInputMontly {
            itemMonthlyId: number;
            itemMontlyName: string;
            displayNumber: number;
            userCanUpdateAtr: number;
            toUse: boolean;
            canBeChangedByOthers: boolean;
            youCanChangeIt: boolean;
            constructor(itemMonthlyId: number,
                itemMontlyName: string,
                displayNumber: number,
                userCanUpdateAtr: number,
                toUse: boolean,
                canBeChangedByOthers: boolean,
                youCanChangeIt: boolean) {
                this.itemMonthlyId = itemMonthlyId;
                this.itemMontlyName = itemMontlyName;
                this.displayNumber = displayNumber;
                this.userCanUpdateAtr = userCanUpdateAtr;
                this.toUse = toUse;
                this.canBeChangedByOthers = canBeChangedByOthers;
                this.youCanChangeIt = youCanChangeIt;

            }

        }

    }
    enum TypeInput {
        
        DAILY = 1,
        
        MONTHLY = 2
        
    }
}
function useChanged(element, rowId, userCanSet) {
    //update lai element
    
    var value = $("#grid").igGrid("getCellValue", rowId, "toUse");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "toUse", value != true);

    //get value youCanChangeIt,canBeChangedByOthers
    var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
    var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
    //if toUse = true and nameLine = 1
    if (value == true && userCanSet.toString() === "1") {
        //set value and disable
        cellYouCanChangeIt.addClass('readOnlyColorIsUse');
        cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
        cellYouCanChangeIt.children().prop("disabled", true);
        cellCanBeChangedByOthers.children().prop("disabled", true);
    } else if (value != true && userCanSet.toString() === "1") {
        cellYouCanChangeIt.removeClass('readOnlyColorIsUse');
        cellCanBeChangedByOthers.removeClass('readOnlyColorIsUse');
        cellYouCanChangeIt.children().prop("disabled", false);
        cellCanBeChangedByOthers.children().prop("disabled", false);
    } else {
        cellYouCanChangeIt.children().prop("disabled", true);
        cellCanBeChangedByOthers.children().prop("disabled", true);
    }
    
    let temp = $('#grid').data('igGrid').dataSource._data;
    let notUseExist = _.find(temp, function(e){ return !e.toUse });
    if(nts.uk.util.isNullOrUndefined(notUseExist)){
        $("#useCheckAll").prop('checked', true);
    }else{
        $("#useCheckAll").prop('checked', false);
    }
}


function youCanChangeItChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "youCanChangeIt");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "youCanChangeIt", value != true);
    
    let temp = $('#grid').data('igGrid').dataSource._data;
    let notYouCanCheckAll = true;
    for(let i =0;i<temp.length;i++){
        if(temp[i].toUse ==true && temp[i].userCanUpdateAtr == 1){
            if(!temp[i].youCanChangeIt){
                notYouCanCheckAll =false;
                break;
            }
        }
    }
    $("#youCanCheckAll").prop('checked', notYouCanCheckAll);
    
    
    
   
}
function canBeChangedByOthersChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "canBeChangedByOthers");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "canBeChangedByOthers", value != true);
    
    
    let temp = $('#grid').data('igGrid').dataSource._data;
    let notOtherCheckAll = true;
    for(let i =0;i<temp.length;i++){
        if(temp[i].toUse ==true && temp[i].userCanUpdateAtr == 1){
            if(!temp[i].canBeChangedByOthers){
                notOtherCheckAll =false;
                break;
            }
        }
    }
    $("#otherCheckAll").prop('checked', notOtherCheckAll);
}

function userCanSetChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "userCanSet");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "userCanSet", value != true);
}

function useHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "toUse", true);
            var rowId = filteredData[i].itemDailyID;
            var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
            var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
            if (filteredData[i].userCanUpdateAtr.toString() === "1") {
                if (cellYouCanChangeIt.hasClass('readOnlyColorIsUse')) {
                    cellYouCanChangeIt.removeClass('readOnlyColorIsUse');
                }
                if (cellCanBeChangedByOthers.hasClass('readOnlyColorIsUse')) {
                    cellCanBeChangedByOthers.removeClass('readOnlyColorIsUse');
                }
                cellYouCanChangeIt.children().prop("disabled", false);
                cellCanBeChangedByOthers.children().prop("disabled", false);
            } else {
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            }
        }

    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "toUse", false);
            var rowId = filteredData[i].itemDailyID;
            var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
            var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
            if (filteredData[i].userCanUpdateAtr.toString() === "1") {
                if (!cellYouCanChangeIt.hasClass('readOnlyColorIsUse')) {
                    cellYouCanChangeIt.addClass('readOnlyColorIsUse');
                }
                if (!cellCanBeChangedByOthers.hasClass('readOnlyColorIsUse')) {
                    cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
                }
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            } else {
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            }
        }

    }
}


function youCanChangeItHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanUpdateAtr.toString() === "1" && filteredData[i].toUse) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "youCanChangeIt", true);
            }
        }
    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanUpdateAtr.toString() === "1" && filteredData[i].toUse) {
                //update 
                $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "youCanChangeIt", false);
            }
        }
    }

}

function canBeChangedByOthersHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanUpdateAtr.toString() === "1" && filteredData[i].toUse) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "canBeChangedByOthers", true);
            }
        }
    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanUpdateAtr.toString() === "1" && filteredData[i].toUse) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].itemDailyID, "canBeChangedByOthers", false);
            }
        }

    }
}


