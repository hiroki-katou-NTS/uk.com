module nts.uk.at.view.kaf006.shr.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;

    export class Kaf006ShrViewModel extends ko.ViewModel {
        create() {

        }

        mounted() {

        }

        public static openDialogKDL035(params: any) {
            console.log("Open KDL035");

            modal("/view/kdl/035/a/index.xhtml").onClosed(() => {
                // get List<振休振出紐付け管理> from KDL035
                const linkingDates: Array<any> = getShared('linkingDates');
                console.log(linkingDates);
            });
        }

        public static openDialogKDL036(params: any) {
            console.log("Open KDL036");
          modal("/view/kdl/036/a/index.xhtml").onClosed(() => {
            let listParam = getShared("KDL036_SHAREPARAM");
            console.log(listParam);
          });
        }
    }

    export class WorkType {
        workTypeCode: string;
        name: string;
        
        constructor(iWorkType: IWorkType) {
            this.workTypeCode = iWorkType.workTypeCode;
            this.name = iWorkType.name;
        }
    }

    export interface IWorkType {
        workTypeCode: string;
        name: string;
    }
}