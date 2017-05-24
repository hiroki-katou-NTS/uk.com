module nts.uk.pr.view.kmf001.b {
    export module service {
        export module model {
            export class EnumerationModel {

                value: number;
                name: string;

                constructor(value: number, name: string) {
                    let self = this;
                    self.name = name;
                    self.value = value;
                }
            }
        }
    }
}