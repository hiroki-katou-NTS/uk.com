module nts.uk.at.view.kdw010.a {
    export module service {
        var path: any = {
            findContinuousHolCheckSet: "",
            saveContinuousHolCheckSet: ""
        }
        export function findContinuousHolCheckSet(): JQueryPromise<model.ContinuousHolCheckSet> {
            return nts.uk.request.ajax("at", path.findContinuousHolCheckSet);
        }
    }
    export module model {
        export class ContinuousHolCheckSet {
            targetWorkType: Array<String>;
            ignoreWorkType: Array<String>;
            useAtr: boolean;
            displayMessage: String;
            maxContinuousDays: number;
            isUpdate: boolean;
            constructor(targetWorkType:Array<String>,ignoreWorkType: Array<String>,useAtr: boolean, displayMessage: String,maxContinuousDays: number,isUpdate: boolean) {
                this.targetWorkType = targetWorkType;
                this.ignoreWorkType = ignoreWorkType;
                this.useAtr = useAtr;
                this.displayMessage = displayMessage;
                this.maxContinuousDays = maxContinuousDays;
                this.isUpdate = isUpdate;
            }
        }

    }
}