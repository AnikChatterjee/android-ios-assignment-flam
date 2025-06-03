import Foundation


struct Song {
    let id: UUID
    let title: String
    let artist: String
    let duration: TimeInterval
    let source: MusicSourceType
}

enum MusicSourceType: String {
    case local = "Local"
    case spotify = "Spotify"
}


protocol MusicSource {
    var name: String { get }
    func fetchSongs() -> [Song]
}

class LocalMusicSource: MusicSource {
    let name = "Local"
    func fetchSongs() -> [Song] {
        return [
            Song(id: UUID(), title: "Local Song A", artist: "Artist A", duration: 180, source: .local),
            Song(id: UUID(), title: "Local Song B", artist: "Artist B", duration: 200, source: .local)
        ]
    }
}

class SpotifyMusicSource: MusicSource {
    let name = "Spotify"
    func fetchSongs() -> [Song] {
        return [
            Song(id: UUID(), title: "Spotify Song X", artist: "Artist X", duration: 240, source: .spotify),
            Song(id: UUID(), title: "Spotify Song Y", artist: "Artist Y", duration: 210, source: .spotify)
        ]
    }
}


class MusicPlayerViewModel {
    private var sources: [MusicSource] = []
    private(set) var playlist: [Song] = []
    private var currentIndex: Int = 0
    private var isPlaying: Bool = false

    init(sources: [MusicSource]) {
        self.sources = sources
        loadSongs()
    }

    private func loadSongs() {
        playlist = sources.flatMap { $0.fetchSongs() }
    }

    func play() {
        guard !playlist.isEmpty else {
            print("Playlist is empty.")
            return
        }
        isPlaying = true
        print("Playing: \(currentSongDescription())")
    }

    func pause() {
        isPlaying = false
        print("Paused.")
    }

    func next() {
        guard !playlist.isEmpty else { return }
        currentIndex = (currentIndex + 1) % playlist.count
        print("Next: \(currentSongDescription())")
    }

    func previous() {
        guard !playlist.isEmpty else { return }
        currentIndex = (currentIndex - 1 + playlist.count) % playlist.count
        print("Previous: \(currentSongDescription())")
    }

    private func currentSongDescription() -> String {
        let song = playlist[currentIndex]
        return "\(song.title) by \(song.artist) [\(song.source.rawValue)]"
    }
}


let local = LocalMusicSource()
let spotify = SpotifyMusicSource()
let viewModel = MusicPlayerViewModel(sources: [local, spotify])

print("Simulated Music Player Session")
viewModel.play()
viewModel.next()
viewModel.pause()
viewModel.previous()
viewModel.play()
