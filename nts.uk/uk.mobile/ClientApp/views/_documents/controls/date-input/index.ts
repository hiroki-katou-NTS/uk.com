import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/date-input',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    }
})
export class DateInputControl extends Vue {

    date: Date = new Date();

    constructor() {
        super();
        let self = this;
    }
}