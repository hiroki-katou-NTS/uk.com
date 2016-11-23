// TreeGrid Node
module qmm019.a.viewmodel {

    export class NodeTest {
        code: string;
        name: string;
        childs: Array<NodeTest>;
        constructor(code: string, name: string, children: Array<NodeTest>) {
            this.code = code;
            this.name = name;
            this.childs = children;
        }
        noteText(): string {
            return this.code + ' ' + this.name;
        }

    }
}