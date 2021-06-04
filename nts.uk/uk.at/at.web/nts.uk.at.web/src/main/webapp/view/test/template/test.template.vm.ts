module nts.uk.at.view.test.template.viewmodel {

    @bean()
    export class ViewModel extends ko.ViewModel {

        fileName: KnockoutObservable<string> = ko.observable("");
        fileType: KnockoutObservable<number> = ko.observable(1);
        accept: KnockoutObservableArray<string> = ko.observableArray(['.xlsx']);
        fileId: KnockoutObservable<string> = ko.observable("");
        changeList: KnockoutObservableArray<InsertCell> = ko.observableArray([]);
        approverName: KnockoutObservable<string> = ko.observable("");
        status: KnockoutObservable<string> = ko.observable("");

        created() {
            let self = this;
            self.fileType.subscribe(v => {
                if (v === 1) {
                    self.accept(['.xlsx']);
                } else {
                    self.accept(['.pdf']);
                }
            })
        }

        mounted() {

        }

        addChange() {
            let self = this;
            let newLst = self.changeList();
            newLst.push(new InsertCell());
            self.changeList(newLst);
        }

        removeChange(index: number) {
            let self = this;
            let newLst = self.changeList();
            newLst.splice(index, 1);
            self.changeList(newLst);
        }

        upload() {
            let self = this;
            $("#file-upload").ntsFileUpload({}).done(function(res: any) {
                self.fileId(res[0].id);
                // nts.uk.request.specials.donwloadFile(self.fileId());
            }).fail(function(err: any) {
                nts.uk.ui.dialog.alertError(err);
            });
        }

        download() {
            let self = this;
            // nts.uk.request.specials.donwloadFile(this.fileId());
            let query = {
                fileName: self.fileName(),
                fileID: self.fileId(),
                approverName: self.approverName(),
                status: self.status(),
                isExcel: self.fileType() == 1 ? true : false
            }

            nts.uk.request.exportFile(paths.export, query);
        }
    }

    class InsertCell {
        id: KnockoutObservable<string>;
        content: KnockoutObservable<string>;

        constructor() {
            this.id = ko.observable("");
            this.content = ko.observable("");
        }
    }

    const paths = {
        export: 'at/template/request/export'
    }
}