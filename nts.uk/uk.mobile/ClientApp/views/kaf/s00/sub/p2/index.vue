<template>
<div class="kafs00subp2">
    <div v-if="params">
        <table class="overtime-list table table-sm mb-0" style="text-align: center">
            <thead class="thead-light">
                <tr class="table-light">
                    <td>{{'KAFS00_30' | i18n}}</td>
                    <td>{{'KAFS00_30' | i18n}}</td>
                    <td>{{'KAFS00_26' | i18n}}</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>{{ params.currentMonth || i18n }}</td>
                    <td>{{ 1500 | timedr }}</td>
                    <td>{{ 1500 | timedr }}</td>
                </tr>
                <tr>
                    <td>{{ params.nextMonth || i18n }}</td>
                    <td>{{ 1500 | timedr }}</td>
                    <td>{{ 1500 | timedr }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
</template>