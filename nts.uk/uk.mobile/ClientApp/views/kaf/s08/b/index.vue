<template>
<div class="kafs08b">
    <h3>Hello {{title | i18n}} component!</h3>
    <h3> Date change is {{date | i18n}} </h3> 
    <child
        v-bind:title='date'
        v-on:xyz="onChangeTitle"
        v-on:changeDateSetting="changeDateSetting"
        />
</div>
</template>