import Foundation


struct Post {
    let id: UUID
    let username: String
    let content: String
    let imageURL: String?
}


class FeedViewModel {
    private(set) var posts: [Post] = []
    
    func loadInitialFeed() {
        posts = [
            Post(id: UUID(), username: "@swiftUser", content: "Hello, Swift World! ", imageURL: nil),
            Post(id: UUID(), username: "@devGal", content: "Check out my iOS app!", imageURL: "image1.png"),
            Post(id: UUID(), username: "@techNinja", content: "Combine is Fire!!", imageURL: nil)
        ]
        print("Initial feed loaded with \(posts.count) posts.")
        printFeed()
    }

    func pullToRefresh() {
        let newPost = Post(id: UUID(), username: "@newJoiner", content: "Glad to join the dev community!", imageURL: nil)
        posts.insert(newPost, at: 0)
        print("\nPull to Refresh triggered.")
        printFeed()
    }

    func printFeed() {
        for (index, post) in posts.enumerated() {
            print("\nPost \(index + 1):")
            print("User: \(post.username)")
            print("Content: \(post.content)")
            if let image = post.imageURL {
                print("Image: \(image)")
            }
        }
    }
}


let viewModel = FeedViewModel()
viewModel.loadInitialFeed()

viewModel.pullToRefresh()
