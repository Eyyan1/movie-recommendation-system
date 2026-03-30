<script lang="ts">
	import SectionHeader from '$lib/components/SectionHeader.svelte';
	import { ratingHistory } from '$lib/data/mock-movies';
</script>

<svelte:head>
	<title>My Ratings</title>
</svelte:head>

<div class="space-y-8">
	<SectionHeader
		eyebrow="Activity"
		title="A clean rating history view"
		description="This route is prepared for the backend ratings endpoint and shows the intended card/list treatment for a user’s rating history."
	/>

	<section class="overflow-hidden rounded-[1.75rem] border border-white/10 bg-white/5">
		<div class="grid grid-cols-[1.6fr_0.7fr_0.8fr] gap-4 border-b border-white/10 px-6 py-4 text-xs font-semibold uppercase tracking-[0.22em] text-slate-400">
			<span>Movie</span>
			<span>Your rating</span>
			<span>Updated</span>
		</div>
		{#each ratingHistory as row}
			<div class="grid grid-cols-[1.6fr_0.7fr_0.8fr] gap-4 border-b border-white/5 px-6 py-5 text-sm text-slate-200 last:border-b-0">
				<span class="font-medium text-white">{row.title}</span>
				<span>{row.score}</span>
				<span class="text-slate-400">{row.updatedAt}</span>
			</div>
		{/each}
	</section>
</div>
