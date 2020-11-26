module nts.uk.at.view.kaf006.shr.viewmodel {

    export class WorkType {
        workTypeCode: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        displayName: KnockoutComputed<string>;
        
        constructor(iWorkType: IWorkType) {
            this.workTypeCode = ko.observable(iWorkType.workTypeCode);
            this.name = ko.observable(iWorkType.name);
            this.displayName = ko.computed(() => this.workTypeCode() + ' ' + this.name());
        }
    }

    export interface IWorkType {
        workTypeCode: string;
        name: string;
    }
}