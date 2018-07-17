module nts.uk.at.view.kdw010.a {
    export module service {
        var path: any = {
            findContinuousHolCheckSet: "at/record/erroralarm/otkcustomize/findContinuousHolCheckSet",
            saveContinuousHolCheckSet: "at/record/erroralarm/otkcustomize/saveContinuousHolCheckSet",
            findAllWorkTypeDto: "at/record/erroralarm/otkcustomize/findAllWorkTypeDto"
        }
        /**
         * find ContinuousHolCheckSet
         */
        export function findContinuousHolCheckSet(): JQueryPromise<model.ContinuousHolCheckSet> {
            return nts.uk.request.ajax("at", path.findContinuousHolCheckSet);
        }
        /**
         * save ContinuousHolCheckSet
         */
        export function saveContinuousHolCheckSet(continuousHolCheckSet: model.ContinuousHolCheckSet) {
            return nts.uk.request.ajax("at", path.saveContinuousHolCheckSet, continuousHolCheckSet);
        }
        /**
         * find all OtkWorkTypeDto
         */
        export function findAllWorkTypeDto(): JQueryPromise<Array<model.OtkWorkTypeDto>> {
            return nts.uk.request.ajax("at", path.findAllWorkTypeDto);
        }
    }
    export module model {
        export class ContinuousHolCheckSet {
            targetWorkType: Array<String>;
            ignoreWorkType: Array<String>;
            useAtr: boolean;
            displayMessage: String;
            maxContinuousDays: String;
            updateMode: boolean;
            constructor(targetWorkType: Array<String>, ignoreWorkType: Array<String>, useAtr: boolean, displayMessage: String, maxContinuousDays: String, updateMode: boolean) {
                this.targetWorkType = targetWorkType;
                this.ignoreWorkType = ignoreWorkType;
                this.useAtr = useAtr;
                this.displayMessage = displayMessage;
                this.maxContinuousDays = maxContinuousDays;
                this.updateMode = updateMode;
            }
        }
        export class OtkWorkTypeDto {
            code: String;
            name: String;
            constructor(code: String, name: String) {
                this.code = code;
                this.name = name;
            }
        }

    }
}