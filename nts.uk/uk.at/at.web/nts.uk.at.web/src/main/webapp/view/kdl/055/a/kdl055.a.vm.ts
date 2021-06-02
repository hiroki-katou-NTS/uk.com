/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import TargetOrgIdenInfor = nts.uk.at.view.kdl055.shr.TargetOrgIdenInfor;
import characteristics = nts.uk.characteristics;

module nts.uk.at.view.kdl055.a.viewmodel {

    @bean()
    export class KDL055ViewModel extends ko.ViewModel {
        scheduleImport: ScheduleImport = new ScheduleImport({mappingFile: null, captureCheckSheet: false, captureSheet: null, captureCheckCell: false, captureCell: null, overwrite: false});
        filename: KnockoutObservable<string> = ko.observable(null);
        fileID: KnockoutObservable<string> = ko.observable(null);
        overwriteOptions: any = ko.observableArray([
            { overwrite: true, name: this.$i18n("KDL055_20") },
            { overwrite: false, name: this.$i18n("KDL055_21") }
        ]);
        targetInfo: TargetOrgIdenInfor;
        sIDs: string[];
        startDate: string;
        endDate: string;
        

        created(params: KDL055Param) {
            const vm = this;
            characteristics.restore('ScheduleImport').then((obj: IScheduleImport) => {
                if (obj) {
                    vm.scheduleImport.bindingData(obj);
                }
            });

            if (params) {
                vm.targetInfo = params.targetInfo;
                vm.sIDs = params.sIDs;
                vm.startDate = params.startDate;
                vm.endDate = params.endDate;
            }
        }

        mounted() {
            const vm = this;

            $('#file-upload button').focus();

            vm.filename.subscribe(value => {
                if (value) {
                    vm.scheduleImport.mappingFile(value);
                }
            });

            vm.scheduleImport.mappingFile.subscribe(value => {
                if (value) {
                    vm.filename(value)
                }
            });

            // $('.fileinput').change(() => {
            //     let filePath = $('.fileinput').val();
            //     vm.filename(filePath);
            // });
        }

        intakeInput() {
            const vm = this;
            vm.upload();
            
        }
        
        upload() {
            const vm = this;
            
            vm.$blockui('show');
            // call api upload file
            $('#file-upload').ntsFileUpload({ stereoType: "excelFile" }).done((res: any) => {
                console.log(res);
                vm.fileID(res[0].id);
                vm.filename(res[0].originalName);
                // save to cache
                characteristics.save('ScheduleImport', ko.toJS(vm.scheduleImport));
            }).fail((err: any) => {
                if (err) {
                    vm.$dialog.error({ messageId: err.messageId, messageParams: err.parameterIds });
                }
            }).always(vm.$blockui('hide'));
        }

        outputExcel() {
            const vm = this;

            let query = {
                employeeIDs: vm.sIDs,
                startDate: vm.startDate,
                endDate: vm.endDate
            }

            vm.$blockui('show');
            nts.uk.request.exportFile(paths.exportTemplate, query).done((data) => {
                if (data) {
                    console.log(data);
                }
            }).fail((err) => {
                if (err) {
                    console.log(err)
                }
            }).always(() => vm.$blockui('hide'));
        }

        close() {
            this.$window.close();
        }
    }

    const paths = {
        exportTemplate: 'wpl/schedule/report/print',
        checkFileUpload: "wpl/schedule/report/checkFileUpload"
    }

    export class KDL055Param {
        targetInfo: TargetOrgIdenInfor;
        sIDs: string[];
        startDate: string;
        endDate: string;
        
        constructor(unit: number, workplaceId: string, workplaceGroupId: string, sIDs: string[], startDate: string, endDate: string) {
            this.targetInfo = { unit: unit, workplaceId: workplaceId, workplaceGroupId: workplaceGroupId };
            this.sIDs = _.isEmpty(sIDs) ? [] : sIDs;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    interface IScheduleImport {
        // 取込ファイル
        mappingFile: string;
        // 取込シート_チェック
        captureCheckSheet: boolean;
        // 取込シート
        captureSheet: string;
        // 取込開始セル_チェック
        captureCheckCell: boolean;
        // 取込開始セル
        captureCell: string;
        // 上書き指定
        overwrite: boolean;
    }

    // 個人スケジュールの取込
    class ScheduleImport {
        // 取込ファイル
        mappingFile: KnockoutObservable<string> = ko.observable(null);
        // 取込シート_チェック
        captureCheckSheet: KnockoutObservable<boolean> = ko.observable(false);
        // 取込シート
        captureSheet: KnockoutObservable<string> = ko.observable(null);
        // 取込開始セル_チェック
        captureCheckCell: KnockoutObservable<boolean> = ko.observable(false);
        // 取込開始セル
        captureCell: KnockoutObservable<string> = ko.observable(null);
        // 上書き指定
        overwrite: KnockoutObservable<boolean> = ko.observable(false);

        constructor(IScheduleImport: IScheduleImport) {
            this.mappingFile(IScheduleImport.mappingFile);
            this.captureCheckSheet(IScheduleImport.captureCheckSheet);
            this.captureSheet(IScheduleImport.captureSheet);
            this.captureCheckCell(IScheduleImport.captureCheckCell);
            this.captureCell(IScheduleImport.captureCell);
            this.overwrite(IScheduleImport.overwrite);
        }

        bindingData(IScheduleImport: IScheduleImport) {
            this.mappingFile(IScheduleImport.mappingFile);
            this.captureCheckSheet(IScheduleImport.captureCheckSheet);
            this.captureSheet(IScheduleImport.captureSheet);
            this.captureCheckCell(IScheduleImport.captureCheckCell);
            this.captureCell(IScheduleImport.captureCell);
            this.overwrite(IScheduleImport.overwrite);
        }
    }
}