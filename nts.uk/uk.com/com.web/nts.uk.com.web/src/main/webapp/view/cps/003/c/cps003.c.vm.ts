module nts.uk.com.view.cps003.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import modeless = nts.uk.ui.windows.sub.modeless;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        baseDate: KnockoutObservable<Date> = ko.observable();
        updateMode: KnockoutObservable<any> = ko.observable();
        category: {
            catId: KnockoutObservable<string>;
            cate: KnockoutObservable<any>;
        } = {
            catId: ko.observable(""),
            cate: ko.observable()
        };
        
        gridOptions = { dataSource: [], columns: [], features: [], ntsControls: [] };
        
        constructor() {
            let self = this;

            self.start();
        }

        start() {
            let self = this;
            
            let param = getShared("CPS003G_PARAM");
            self.baseDate(param.baseDate);
            self.updateMode(param.updateMode);
            self.category.catId(param.catId);
            self.category.cate(param.cate);
            
            let list: IRequestData = getShared("CPS003G_LIST");
            
            self.convertData(list).done(() => {
                // self.loadGrid();
            });
        }
        
        convertData(data: IRequestData) {
            let self = this, dfd = $.Deferred();
            
            return dfd.promise();
        }
        
        loadGrid() {
            let self = this;
            if ($("#grid").data("mGrid")) $("#grid").mGrid("destroy");
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1000px",
                height: "800px",
                headerHeight: "80px",
                subHeight: "380px",
                subWidth: "160px",
                dataSource: self.gridOptions.dataSource,
                primaryKey: "id",
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: true,
                errorColumns: [ "employeeCode", "employeeName", "rowNumber" ],
                columns: self.gridOptions.columns,
                features: self.gridOptions.features,
                ntsControls: self.gridOptions.ntsControls
            }).create();
        }
        
        register() {
            let self = this,
                command, employees = [], recId = {};
            
            let itemErrors = $("#grid").mGrid("errors");
            if (itemErrors && itemErrors.length > 0) {
                // TODO: Show dialog
                return;
            }
            
            block();
            _.forEach($("#grid").mGrid("dataSource"), d => recId[d.id] = d);
            let cateName, cateType, regId = {};
            if (self.category.cate()) {
                cateName = self.category.cate().categoryName;
                cateType = self.category.cate().categoryType;
            }
            
            _.forEach($("#grid").mGrid("updatedCells"), item => {
                if (item.columnKey === "register") return;
                let recData: Record = recId[item.rowId];
                let regEmp = regId[recData.id];
                if (!regEmp) {
                    regEmp = { personId: recData.personId, employeeId: recData.employeeId, employeeCd: recData.employeeCode, employeeName: recData.employeeName, order: recData.rowNumber };
                    regEmp.input = { categoryId: self.category.catId(), categoryCd: self.category.catCode(), categoryName: cateName, categoryType: cateType, recordId: recData.id, delete: false, items: [] };
                    regId[recData.id] = regEmp;
                }
                
                let col = _.find(self.gridOptions.columns, column => column.key === item.columnKey);
                if (col) {
                    let val = item.value;
                    if (item.value instanceof Date) {
                        val = moment(item.value).format("YYYY/MM/DD");
                    }
                    
                    regEmp.input.items.push({ definitionId: col.itemId, itemCode: col.key, itemName: col.itemName, value: val, text: val, defValue: val, defText: val, type: col.perInfoTypeState.dataTypeValue, logType: col.perInfoTypeState.dataTypeValue });
                }
                
                employees.push(regEmp);
            });
            
            command = { baseDate: self.baseDate(), editMode: self.updateMode(), employees: employees };
            service.push.register(command).done((errorList) => {
                info({ messageId: "Msg_15" }).then(() => {
                    unblock();
                    
                });
            }).fail((res) => {
                unblock();
                alert(res.message);
            });
        }
        
        checkError() {
            let self = this, $grid = $("#grid");
            $grid.mGrid("validate");
            let errors = $grid.mGrid("errors");
            if (errors.length === 0) {
                nts.uk.ui.dialog.info({ messageId: "Msg_1463" });
                return;
            }
            
            setShared("CPS003G_ERROR_LIST", _.map(errors, err => { return { empCd: err.employeeCode, empName: err.employeeName, no: err.rowNumber, itemName: err.columnName, message: err.message }; }));
            modeless("/view/cps/003/g/index.xhtml").onClosed(() => {
                
            });
        }
        
        close() {
            nts.uk.ui.windows.close();
        }
    }
    
    class Record {
        rowNumber: number;
        id: string;
        personId: string;
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        classCode: string;
        className: string;
        deptCode: string;
        deptName: string;
        employmentCode: string;
        employmentName: string;
        positionCode: string;
        positionName: string;
        workplaceCode: string;
        workplaceName: string;
        register: boolean;
        
        constructor(data: IDataBody, rowNumber: number) {
            this.rowNumber = rowNumber + 1;
            this.id = (data.items && data.items[0] && data.items[0].recordId) || nts.uk.util.randomId() + "_noData";
            this.personId = data.personId;
            this.employeeId = data.employeeId;
            this.employeeCode = data.employee.code;
            this.employeeName = data.employee.name;
            this.classCode = data.classification.code;
            this.className = data.classification.name;
            this.deptCode = data.department.code;
            this.deptName = data.department.name;
            this.employmentCode = data.employment.code;
            this.employmentName = data.employment.name;
            this.positionCode = data.position.code;
            this.positionName = data.position.name;
            this.workplaceCode = data.workplace.code;
            this.workplaceName = data.workplace.name;
            this.register = false;
        }
    }
    
    interface IRequestData {
        baseDate: string;
        categoryId: string;
        headDatas: IDataHead[];
        bodyDatas: IDataBody[];
    }

    /* Dữ liệu  */
    export interface IDataHead {
        itemId: string;
        itemCode: string;
        itemName: string;
        itemOrder: number;
        itemParentCode: string;
        itemTypeState: ISingleItem;
        required: boolean;
        resourceId: string;
    }

    /* Dữ liệu body (điều chỉnh thêm) */
    interface IDataBody {
        personId: string;
        employeeId: string;
        classification: ICodeName;
        department: ICodeName;
        employee: ICodeName;
        employeeBirthday: string;
        employment: ICodeName;
        position: ICodeName;
        workplace: ICodeName;
        items: IColumnData[];
    }

    interface ICodeName {
        code: string;
        name: string;
    }

    /* Dữ liệu tương ứng từng cột */
    interface IColumnData {
        actionRole: ACTION_ROLE;
        itemCode: string;
        itemParentCode: string;

        lstComboBoxValue: any[]; // list data để validate 

        recordId: string | null; // id bản ghi trong db
        textValue: string | null; // giá trị hiển thị 
        value: Object | null; // giá trị
    }

    export enum ACTION_ROLE {
        HIDDEN = <any>"HIDDEN",
        VIEW_ONLY = <any>"VIEW_ONLY",
        EDIT = <any>"EDIT"
    }

    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    export enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6,
        SEL_RADIO = 7,
        SEL_BUTTON = 8,
        READONLY = 9,
        RELATE_CATEGORY = 10,
        NUMBERIC_BUTTON = 11,
        READONLY_BUTTON = 12
    }
}
