module nts.uk.at.view.test.template.viewmodel {

    @bean()
    export class ViewModel{

        fileName: KnockoutObservable<string> = ko.observable("");
        fileType: KnockoutObservable<number> = ko.observable(1);
        accept: KnockoutObservableArray<string> = ko.observableArray(['.xls','.xlsx']);
        fileId: KnockoutObservable<string> = ko.observable("");
        changeList: KnockoutObservableArray<InsertCell> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.fileType.subscribe(v => {
                if (v === 1) {
                    self.accept(['.xls','.xlsx']);
                } else {
                    self.accept(['.pdf']);
                }
            })
        }

        start(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();

            dfd.resolve();
            return dfd.promise();
        }

        addChange() {
            let self = this;
            let newLst = self.changeList();
            newLst.push(new InsertCell());
            self.changeList(newLst);
        }

        removeChange(index) {
            let self = this;
            let newLst = self.changeList();
            newLst.splice(index, 1);
            self.changeList(newLst);
        }

        upload() {
            let self = this;
            $("#file-upload").ntsFileUpload({}).done(function(res) {
                self.fileId(res[0].id);
                nts.uk.request.specials.donwloadFile(self.fileId());
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
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
}