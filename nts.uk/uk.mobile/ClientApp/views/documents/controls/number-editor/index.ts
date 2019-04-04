import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/number-editor',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    }
})
export class NumberEditorControl extends Vue {

    number: number = 10;

}