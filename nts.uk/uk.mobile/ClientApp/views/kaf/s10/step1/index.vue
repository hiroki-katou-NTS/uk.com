<template>
<div class="kafs10step1">
    <h3>Hello {{title | i18n}} component step1!</h3>
    <nts-text-editor
        name='title'
        v-model='title' />
</div>
</template>