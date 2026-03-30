<script lang="ts">
	import type { MovieCardData } from '$lib/data/mock-movies';

	let { movie, compact = false }: { movie: MovieCardData; compact?: boolean } = $props();
</script>

<article class="group overflow-hidden rounded-[1.75rem] border border-white/10 bg-white/5 shadow-xl shadow-slate-950/25 transition hover:-translate-y-1 hover:bg-white/[0.07]">
	<div class={`relative ${compact ? 'h-56' : 'h-72'} overflow-hidden`}>
		<div class={`absolute inset-0 bg-gradient-to-br ${movie.posterGradient}`}></div>
		<div class="absolute inset-0 bg-[radial-gradient(circle_at_top,rgba(255,255,255,0.35),transparent_45%)] opacity-60"></div>
		<div class="absolute bottom-4 left-4 rounded-full border border-white/25 bg-slate-950/55 px-3 py-1 text-xs font-semibold uppercase tracking-[0.18em] text-white">
			{movie.releaseYear}
		</div>
	</div>
	<div class="space-y-4 p-5">
		<div class="flex items-start justify-between gap-3">
			<div>
				<h3 class="font-display text-2xl text-white">{movie.title}</h3>
				<p class="mt-1 text-sm text-slate-400">{movie.genres.join(' • ')}</p>
			</div>
			<div class="rounded-full bg-amber-300 px-3 py-1 text-sm font-bold text-slate-950">{movie.rating}</div>
		</div>
		<p class="line-clamp-3 text-sm leading-7 text-slate-300">{movie.description}</p>
	</div>
</article>
