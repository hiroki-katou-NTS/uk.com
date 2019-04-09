import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/text-area',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    }
})
export class TextareaControl extends Vue {

    text: string = 'Init Value Area';

}