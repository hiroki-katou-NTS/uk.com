/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import characteristics = nts.uk.characteristics;
import IScheduleImport = nts.uk.at.view.kdl055.a.viewmodel.IScheduleImport;

module nts.uk.at.view.kdl055.b.viewmodel {

    @bean()
    export class KDL055BViewModel extends ko.ViewModel {
        overwrite: boolean = false;
        filename: KnockoutObservable<string> = ko.observable(null);
        gridOptions = { dataSource: [], columns: [], features: [], ntsControls: [] };
        data: CaptureDataOutput = null;
        isOpenKDL053: boolean = false;

        created(params: any) {
            const vm = this;

            characteristics.restore('ScheduleImport').then((obj: IScheduleImport) => {
                if (obj) {
                    vm.overwrite = obj.overwrite;
                    vm.filename(obj.mappingFile);
                }
            });

            if (params) {
                // vm.$blockui('show');
                // vm.$ajax(paths.getCaptureData, { data: params, overwrite: vm.overwrite }).done((res: CaptureDataOutput) => {
                //     if (res) {
                //         console.log(res);
                //         data = res;
                //         vm.convertToGrid(res);
                //         vm.loadGrid();
                //     }
                // }).fail((err: any) => {
                //     if (err) {
                //         vm.$dialog.error({ messageId: err.messageId, messageParams: err.parameterIds });
                //     }
                // })

                vm.data = dataFake;
                vm.convertToGrid(vm.data);
            }
        }
        
        mounted() {
            const vm = this;

            vm.loadGrid();
            vm.loadError(vm.data);
        }

        register() {

        }

        openKDL053() {
            const vm = this;

            let mappingErrorList = vm.data.mappingErrorList;
            let request: any = {};
            request.errorRegistrationList = [];
            for (let i = 0; i < mappingErrorList.length; i++) {
                let item = {id: i, sid: mappingErrorList[i].employeeCode, scd: mappingErrorList[i].employeeCode, empName: mappingErrorList[i].employeeName, 
                    date: mappingErrorList[i].date, attendanceItemId: null, errorMessage: vm.$i18n.message(mappingErrorList[i].errorMessage)};
                request.errorRegistrationList.push(item);
            }
            request.isRegistered = 0;
            request.dispItemCol = true;
            request.employeeIds = vm.data.listPersonEmp;
            if (!vm.isOpenKDL053) {
                vm.$window.modeless('at', '/view/kdl/053/a/index.xhtml', request).then(() => {
                    vm.isOpenKDL053 = false;
                });
                vm.isOpenKDL053 = true;
            }
        }

        close() {
            // setShared('openA', openA);
            this.$window.close();
        }

        loadGrid() {
            let vm = this;

            if ($("#grid").data("mGrid")) $("#grid").mGrid("destroy");
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: '1200px',
                height: '600px',
                headerHeight: "40px",
                subHeight: "140px",
                subWidth: "100px",
                dataSource: vm.gridOptions.dataSource,
                columns: vm.gridOptions.columns,
                primaryKey: 'employeeId',
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: false,
                features: vm.gridOptions.features,
                ntsControls: vm.gridOptions.ntsControls
            }).create();
        }

        loadError(param: CaptureDataOutput) {
            const vm = this;

            let mappingErrorList = param.mappingErrorList;
            let errors = [];
            
            _.forEach(mappingErrorList, (error: MappingErrorOutput) => {
                let err = { columnKey: 'nameHeader', id: null, index: null, message: vm.$i18n.message(error.errorMessage) };
                
                if (error.employeeCode) {
                    err.id = error.employeeCode;
                    for (let i = 0; i < param.listPersonEmp.length; i++) {
                        if (param.listPersonEmp[i].employeeId === error.employeeCode) {
                            err.index = i;
                        }
                    }
                }
                if (error.date) {
                    err.columnKey = error.date;
                }
                
                errors.push(err);
            });

            $("#grid").mGrid("setErrors", errors);

            if (mappingErrorList.length > 0) {
                let request: any = {};
                request.errorRegistrationList = [];
                for (let i = 0; i < mappingErrorList.length; i++) {
                    let item = {id: i, sid: mappingErrorList[i].employeeCode, scd: mappingErrorList[i].employeeCode, empName: mappingErrorList[i].employeeName, 
                        date: mappingErrorList[i].date, attendanceItemId: null, errorMessage: vm.$i18n.message(mappingErrorList[i].errorMessage)};
                    request.errorRegistrationList.push(item);
                }
                request.isRegistered = 0;
                request.dispItemCol = false;
                request.employeeIds = param.listPersonEmp;
                vm.$window.modeless('at', '/view/kdl/053/a/index.xhtml', request).then(() => {
                    vm.isOpenKDL053 = false;
                });
                vm.isOpenKDL053 = true;
            }
        }

        convertToGrid(data: CaptureDataOutput) {
            const vm = this;

            let headerStyle = { name: 'HeaderStyles', columns: [{ key: 'nameHeader', colors: ['weekday', 'align-center'] }] };
            let cellStates = [];

            // columns
            let importableDates: string[] = data.importableDates;

            vm.gridOptions.columns.push({ headerText: vm.$i18n('KDL055_26') , itemId: 'nameHeader', key: 'nameHeader', dataType: 'string', width: '150px', columnCssClass: 'halign-left', headerCssClass: 'halign-center valign-center', ntsControl: 'Label' });

            _.forEach(importableDates, (dateString: string) => {
                let item = { headerText: vm.convertDateHeader(dateString), itemId: dateString, key: dateString, dataType: 'string', width: '60px', columnCssClass: 'center-align', headerCssClass: 'center-align', constraint: {primitiveValue: 'ShiftMasterImportCode'} };
                if (_.filter(data.holidays, {'date': dateString}).length > 0 || new Date(dateString).getDay() === 0) {
                    headerStyle.columns.push({ key: dateString, colors: ['sunday', 'align-center'] });
                } else if (new Date(dateString).getDay() === 6) {
                    headerStyle.columns.push({ key: dateString, colors: ['saturday', 'align-center'] });
                } else {
                    headerStyle.columns.push({ key: dateString, colors: ['weekday', 'align-center'] });
                }

                vm.gridOptions.columns.push(item);
                // vm.gridOptions.ntsControls.push({name: dateString, constraint: {primitiveValue: 'ShiftMasterImportCode'}});
            });

            // dataSources
            let listPersonEmp = data.listPersonEmp;
            let results = data.importResult.results;

            _.forEach(listPersonEmp, (emp) => {
                let record = { employeeId: emp.employeeId, employeeCode: emp.employeeCode, employeeName: emp.businessName, nameHeader: emp.employeeCode + ' ' + emp.businessName };
                _.forEach(results, (result: ImportResultDetail) => {
                    if (result.employeeId === emp.employeeId) {
                        record[result.ymd] = result.importCode;
                        
                        if ([2, 3, 4, 5, 9].includes(result.status) || (result.status === 9 && !vm.overwrite)) {
                            cellStates.push({ rowId: emp.employeeId, columnKey: result.ymd, state: ["mgrid-disable", "align-center"]});
                        } else {
                            cellStates.push({ rowId: emp.employeeId, columnKey: result.ymd, state: ["align-center"]});
                        }
                    }
                });
                vm.gridOptions.dataSource.push(record);
            });

            _.forEach(listPersonEmp, (emp) => {
                _.forEach(importableDates, (date: string) => {
                    if (_.filter(results, { 'employeeId': emp.employeeId, 'ymd': date}).length == 0) {
                        cellStates.push({ rowId: emp.employeeId, columnKey: date, state: ["mgrid-disable", "align-center"]});
                    }
                });
            })

            // features
            vm.gridOptions.features = [{ name: 'Copy' }, { name: 'Tooltip', error: true }];
            let columnFixing = { name: 'ColumnFixing', columnSettings: [{ columnKey: 'nameHeader', isFixed: true }]};

            vm.gridOptions.features.push(columnFixing);
            vm.gridOptions.features.push(headerStyle);
            vm.gridOptions.features.push({ name: 'CellStyles', states: cellStates });
        }

        convertDateHeader(text: string): string {
            let date = new Date(text);

            return "<div>" + moment(date).format('M/D') + "<div><div>" + moment(date).format('dd') + "<div>"
        }
    }

    const paths = {
        getCaptureData: 'wpl/schedule/report/getCaptureData'
    }

    export interface ColumnItem {
        headerText: string;

        itemName: string;

        key: string;

        dataType: string;

        width: string;
    }

    export interface Record {
        id: string;

        employeeCode: string;

        ymd: string;

        importCode: string;

        status: number;
    }

    export interface CapturedRawDataDto {
        /** 取り込み内容 **/
        contents: CapturedRawDataOfCellDto[];

        /** 社員の並び順(OrderdList) **/
        employeeCodes: string[];
    }

    export interface CapturedRawDataOfCellDto {
        /** 社員コード **/
        employeeCode: string;
        /** 年月日 **/
        ymd: string;
        /** 取り込みコード **/
        importCode: string;
    }

    export interface CaptureDataOutput {
        // 社員リスト　：OrderedList<社員ID, 社員コード, ビジネスネーム>
        listPersonEmp: PersonEmpBasicInfoImport[];
        // 年月日リスト：OrderedList<年月日, 曜日>
        importableDates: string[];
        // 祝日リスト　：List<祝日>
        holidays: PublicHoliday[];
        // 取り込み結果
        importResult: ImportResult;
        // エラーリスト：List<取り込みエラーDto>
        mappingErrorList: MappingErrorOutput[];
    }

    export interface PersonEmpBasicInfoImport {
        // 個人ID
        personId: string;

        // 社員ID
        employeeId: string;

        // ビジネスネーム
        businessName: string;

        // 性別
        gender: number;

        // 生年月日
        birthday: string;

        // 社員コード
        employeeCode: string;

        // 入社年月日
        jobEntryDate: string;

        // 退職年月日
        retirementDate: string;
    }

    export interface PublicHoliday {
        companyId: string;

        date: string;

        holidayName: string;
    }

    export interface ImportResult {
        /** 1件分の取り込み結果 **/
        results: ImportResultDetail[];
        /** 取り込み不可日 **/
        unimportableDates: string[];
        /** 存在しない社員 **/
        unexistsEmployees: string[];
        /** 社員の並び順(OrderdList) **/
        orderOfEmployees: string[];
    }

    export interface ImportResultDetail {
        /** 社員ID **/
        employeeId: string;
        /** 年月日 **/
        ymd: string;
        /** 取り込みコード **/
        importCode: string;
        /** 状態 **/
        status: number;
    }

    export interface MappingErrorOutput {
        // 社員コード
        employeeCode: string;
        // 社員名
        employeeName: string;
        // 年月日
        date: string;
        // エラーメッセージ
        errorMessage: string;
    }

    export enum ImportStatus {
        // 未チェック
        UNCHECKED = 0,
        // 取込可能
        IMPORTABLE = 1,
        // 参照範囲外
        OUT_OF_REFERENCE = 2,
        // 個人情報不備
        EMPLOYEEINFO_IS_INVALID = 3,
        // 在職していない
        EMPLOYEE_IS_NOT_ENROLLED = 4,
        // スケジュール管理しない 
        SCHEDULE_IS_NOTUSE = 5,
        // シフトが存在しない 
        SHIFTMASTER_IS_NOTFOUND = 6,
        // シフトが不正
        SHIFTMASTER_IS_ERROR = 7,
        // 確定済み
        SCHEDULE_IS_COMFIRMED = 8,
        // すでに勤務予定が存在する
        SCHEDULE_IS_EXISTS = 9
    }

    export interface CellState {
        rowId: string,

        columnKey: string,

        state: any[]
    }

    const listPerson: PersonEmpBasicInfoImport[] = [
        {personId: "000000001", employeeId: '000000001', businessName: 'emp1', gender: 1, birthday: null, employeeCode: '001', jobEntryDate: null, retirementDate: null},
        {personId: "000000002", employeeId: '000000002', businessName: 'emp2', gender: 1, birthday: null, employeeCode: '002', jobEntryDate: null, retirementDate: null},
        {personId: "000000003", employeeId: '000000003', businessName: 'emp3', gender: 1, birthday: null, employeeCode: '003', jobEntryDate: null, retirementDate: null},
        {personId: "000000004", employeeId: '000000004', businessName: 'emp4', gender: 1, birthday: null, employeeCode: '004', jobEntryDate: null, retirementDate: null},
        {personId: "000000005", employeeId: '000000005', businessName: 'emp5', gender: 1, birthday: null, employeeCode: '005', jobEntryDate: null, retirementDate: null},
        {personId: "000000006", employeeId: '000000006', businessName: 'emp6', gender: 1, birthday: null, employeeCode: '006', jobEntryDate: null, retirementDate: null},
        {personId: "000000007", employeeId: '000000007', businessName: 'emp7', gender: 1, birthday: null, employeeCode: '007', jobEntryDate: null, retirementDate: null},
        {personId: "000000008", employeeId: '000000008', businessName: 'emp8', gender: 1, birthday: null, employeeCode: '008', jobEntryDate: null, retirementDate: null},
        {personId: "000000009", employeeId: '000000009', businessName: 'emp9', gender: 1, birthday: null, employeeCode: '009', jobEntryDate: null, retirementDate: null},
        {personId: "000000010", employeeId: '000000010', businessName: 'emp10', gender: 1, birthday: null, employeeCode: '010', jobEntryDate: null, retirementDate: null},
        {personId: "000000011", employeeId: '000000011', businessName: 'emp11', gender: 1, birthday: null, employeeCode: '011', jobEntryDate: null, retirementDate: null},
        {personId: "000000012", employeeId: '000000012', businessName: 'emp12', gender: 1, birthday: null, employeeCode: '012', jobEntryDate: null, retirementDate: null},
        {personId: "000000013", employeeId: '000000013', businessName: 'emp13', gender: 1, birthday: null, employeeCode: '013', jobEntryDate: null, retirementDate: null},
        {personId: "000000014", employeeId: '000000014', businessName: 'emp14', gender: 1, birthday: null, employeeCode: '014', jobEntryDate: null, retirementDate: null},
        {personId: "000000015", employeeId: '000000015', businessName: 'emp15', gender: 1, birthday: null, employeeCode: '015', jobEntryDate: null, retirementDate: null},
        {personId: "000000016", employeeId: '000000016', businessName: 'emp16', gender: 1, birthday: null, employeeCode: '016', jobEntryDate: null, retirementDate: null},
        {personId: "000000017", employeeId: '000000017', businessName: 'emp17', gender: 1, birthday: null, employeeCode: '017', jobEntryDate: null, retirementDate: null},
        {personId: "000000018", employeeId: '000000018', businessName: 'emp18', gender: 1, birthday: null, employeeCode: '018', jobEntryDate: null, retirementDate: null},
        {personId: "000000019", employeeId: '000000019', businessName: 'emp19', gender: 1, birthday: null, employeeCode: '019', jobEntryDate: null, retirementDate: null},
        {personId: "000000020", employeeId: '000000020', businessName: 'emp20', gender: 1, birthday: null, employeeCode: '020', jobEntryDate: null, retirementDate: null}
    ]

    const dates: string[] = [
        '2021/04/01', '2021/04/02', '2021/04/03', '2021/04/04', '2021/04/05', '2021/04/06', '2021/04/07', 
        '2021/04/08', '2021/04/09', '2021/04/10', '2021/04/11', '2021/04/12', '2021/04/13', '2021/04/14', 
        '2021/04/15', '2021/04/16', '2021/04/17', '2021/04/18', '2021/04/19', '2021/04/20', '2021/04/21', 
        '2021/04/22', '2021/04/23', '2021/04/24', '2021/04/25', '2021/04/26', '2021/04/27', '2021/04/28', '2021/04/29', '2021/04/30'
    ]

    const holidays: PublicHoliday[] = [
        {companyId: null, date: '2021/04/02', holidayName: null},
        {companyId: null, date: '2021/04/06', holidayName: null},
        {companyId: null, date: '2021/04/07', holidayName: null},
        {companyId: null, date: '2021/04/14', holidayName: null},
        {companyId: null, date: '2021/04/15', holidayName: null},
        {companyId: null, date: '2021/04/30', holidayName: null},
    ]

    const result: ImportResultDetail[] = [
        {employeeId: '000000001', ymd: '2021/04/01', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/02', importCode: '午前のみ出', status: 2},
        {employeeId: '000000001', ymd: '2021/04/03', importCode: '午前のみ出', status: 3},
        {employeeId: '000000001', ymd: '2021/04/04', importCode: '午前のみ出', status: 4},
        {employeeId: '000000001', ymd: '2021/04/05', importCode: '午前のみ出', status: 5},
        {employeeId: '000000001', ymd: '2021/04/06', importCode: '午前のみ出', status: 8},
        {employeeId: '000000001', ymd: '2021/04/07', importCode: '午前のみ出', status: 9},
        {employeeId: '000000001', ymd: '2021/04/08', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/10', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/11', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/12', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/15', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/16', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/17', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/18', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/19', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/20', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/21', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/22', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/29', importCode: '午前のみ出', status: 1},
        {employeeId: '000000001', ymd: '2021/04/30', importCode: '午前のみ出', status: 1},

        {employeeId: '000000002', ymd: '2021/04/01', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/02', importCode: '出勤A', status: 2},
        {employeeId: '000000002', ymd: '2021/04/03', importCode: '出勤A', status: 3},
        {employeeId: '000000002', ymd: '2021/04/04', importCode: '出勤A', status: 4},
        {employeeId: '000000002', ymd: '2021/04/05', importCode: '出勤A', status: 5},
        {employeeId: '000000002', ymd: '2021/04/06', importCode: '出勤Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', status: 8},
        {employeeId: '000000002', ymd: '2021/04/07', importCode: '出勤A', status: 9},
        {employeeId: '000000002', ymd: '2021/04/09', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/10', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/11', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/13', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/14', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/15', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/23', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/24', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/25', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/26', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/27', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/28', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/29', importCode: '出勤A', status: 1},
        {employeeId: '000000002', ymd: '2021/04/30', importCode: '出勤A', status: 1}, 
        
        {employeeId: '000000003', ymd: '2021/04/01', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/02', importCode: '出勤', status: 2},
        {employeeId: '000000003', ymd: '2021/04/03', importCode: '出勤', status: 3},
        {employeeId: '000000003', ymd: '2021/04/04', importCode: '出勤', status: 4},
        {employeeId: '000000003', ymd: '2021/04/05', importCode: '出勤', status: 5},
        {employeeId: '000000003', ymd: '2021/04/06', importCode: '出勤', status: 8},
        {employeeId: '000000003', ymd: '2021/04/07', importCode: '出勤', status: 9},
        {employeeId: '000000003', ymd: '2021/04/09', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/10', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/11', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/13', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/14', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/15', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/23', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/24', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/25', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/26', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/27', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/28', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/29', importCode: '出勤', status: 1},
        {employeeId: '000000003', ymd: '2021/04/30', importCode: '出勤', status: 1}, 

        {employeeId: '000000004', ymd: '2021/04/01', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/02', importCode: '出勤', status: 2},
        {employeeId: '000000004', ymd: '2021/04/03', importCode: '出勤', status: 3},
        {employeeId: '000000004', ymd: '2021/04/04', importCode: '出勤', status: 4},
        {employeeId: '000000004', ymd: '2021/04/05', importCode: '出勤', status: 5},
        {employeeId: '000000004', ymd: '2021/04/06', importCode: '出勤', status: 8},
        {employeeId: '000000004', ymd: '2021/04/07', importCode: '出勤', status: 9},
        {employeeId: '000000004', ymd: '2021/04/09', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/10', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/11', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/13', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/14', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/15', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/23', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/24', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/25', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/26', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/27', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/28', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/29', importCode: '出勤', status: 1},
        {employeeId: '000000004', ymd: '2021/04/30', importCode: '出勤', status: 1}, 

        {employeeId: '000000005', ymd: '2021/04/01', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/02', importCode: '出勤', status: 2},
        {employeeId: '000000005', ymd: '2021/04/03', importCode: '出勤', status: 3},
        {employeeId: '000000005', ymd: '2021/04/04', importCode: '出勤', status: 4},
        {employeeId: '000000005', ymd: '2021/04/05', importCode: '出勤', status: 5},
        {employeeId: '000000005', ymd: '2021/04/06', importCode: '出勤', status: 8},
        {employeeId: '000000005', ymd: '2021/04/07', importCode: '出勤', status: 9},
        {employeeId: '000000005', ymd: '2021/04/09', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/10', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/11', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/13', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/14', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/15', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/23', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/24', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/25', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/26', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/27', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/28', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/29', importCode: '出勤', status: 1},
        {employeeId: '000000005', ymd: '2021/04/30', importCode: '出勤', status: 1}, 
    ]

    const importResult: ImportResult = {
        results: result,
        unimportableDates: [],
        unexistsEmployees: [],
        orderOfEmployees: []
    }

    const mappingErrorList = [
        {employeeCode: '000000001', employeeName: 'emp1', date: '2021/04/01', errorMessage: 'Msg_2121'},
        {employeeCode: '000000002', employeeName: 'emp2', date: '2021/04/01', errorMessage: 'Msg_2175'},
        {employeeCode: '000000003', employeeName: 'emp3', date: '2021/04/01', errorMessage: 'Msg_2121'},
        {employeeCode: '000000004', employeeName: 'emp4', date: '2021/04/01', errorMessage: 'Msg_2175'},
    ]

    const dataFake: CaptureDataOutput = {
        // 社員リスト　：OrderedList<社員ID, 社員コード, ビジネスネーム>
        listPersonEmp: listPerson,
        // 年月日リスト：OrderedList<年月日, 曜日>
        importableDates: dates,
        // 祝日リスト　：List<祝日>
        holidays: holidays,
        // 取り込み結果
        importResult: importResult,
        // エラーリスト：List<取り込みエラーDto>
        mappingErrorList: mappingErrorList,
    }
}