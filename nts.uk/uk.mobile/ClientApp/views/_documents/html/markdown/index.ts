import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/markdown',
        parent: '/documents'
    },
    template: '<div id="markdown"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/en.md')
    }
})
export class MarkdownComponent extends Vue {
}