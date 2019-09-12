<template>
    <div class="kdws03a">
        <ul class="list-group border-0">
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg">{{'KDWS03_24' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg">{{'KDWS03_25' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03c" v-bind:disabled="errorScreen">{{'KDWS03_30' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03g(0)">{{'KDWS03_31' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03f">{{'KDWS03_32' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03g(1)">{{'KDWS03_33' | i18n}}</button></li>
        </ul>
    </div>
</template>